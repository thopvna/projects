package com.thopv.projects.ikariam.home.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.dialogs.TimeChoosenDialog;
import com.thopv.projects.ikariam.supports.utils.ToastUtils;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.fight.FightActivity;
import com.thopv.projects.ikariam.home.presentation.contracts.HomeContract;
import com.thopv.projects.ikariam.home.presentation.presenters.HomePresenter;
import com.thopv.projects.ikariam.home.presentation.views.EnemyMenuView;
import com.thopv.projects.ikariam.home.presentation.views.HouseBuilderView;
import com.thopv.projects.ikariam.home.presentation.views.MeMenuView;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.units.properties.Coordinate;
import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.servers.FightTimeDistanceManager;

public class HomeActivity extends AppCompatActivity implements HomeContract.Controller {
    private static String TAG = "HomeActivity";
    /**
     focusedHouse trỏ đến house cuối cùng mà chúng ta nhấn vào.
     **/
    private HomePresenter homePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Khởi tạo HomePresenter
        UseCaseComponent useCaseComponent = UseCaseComponent.getInstance(this);
        homePresenter = new HomePresenter(this,
                useCaseComponent.getLoadHouses(),
                useCaseComponent.getGetHouse(),
                useCaseComponent.getAddHouse(),
                useCaseComponent.getAddHomeTroops()
                );
        findViewById(R.id.blueHouseId).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            onBlueImageViewClick();
        });
        findViewById(R.id.redHouseId).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            onRedImageViewClick();
        });
        findViewById(R.id.setupButton).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            onSetupButtonClicked(v);
        });
        //TODO: triển được cái vụ Log toàn tập ở đây thì hay phét.
    }
    @Override
    public void showHouseBuilderDialog(int party) {
        ImageView imageView;
        imageView = getImageView(party);
        Coordinate coordinate = new Coordinate(imageView.getX(), imageView.getY());
        HouseBuilderView dialog = new HouseBuilderView(this, party, coordinate, houseDescription -> {
            homePresenter.buildHouse(houseDescription);
        });
        dialog.show();
    }

    @Override
    public void showMeDialog(House house) {
        new MeMenuView(this, house).show();
    }

    @Override
    public void showEnemyDialog(House house) {
        EnemyMenuView dialog = new EnemyMenuView(this, house,() -> homePresenter.loadField(house));
        dialog.show();
    }

    @Override
    public void showBattleField(House house) {
        Intent intent = new Intent(this, FightActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("house", new Gson().toJson(house));
        startActivity(intent);
    }


    @Override
    public void showError(String err) {
        ToastUtils.getInstance(this).show(err);
    }

    @Override
    public void showHouse(int party, int drawble) {
        ImageView imageView;
        imageView = getImageView(party);
        Picasso.with(this).load(drawble).fit().into(imageView);
    }

    private ImageView getImageView(int party) {
        ImageView imageView;
        if(party == 0){
            imageView = findViewById(R.id.blueHouseId);
        }
        else{
            imageView = findViewById(R.id.redHouseId);
        }
        return imageView;
    }
    public void onBlueImageViewClick(){
        homePresenter.loadHouse(0);
    }
    public void onRedImageViewClick(){
        homePresenter.loadHouse(1);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    private void onSetupButtonClicked(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu = popupMenu.getMenu();
        menu.add(0, 0, 0, "Khoảng cách giữa 2 lần tấn công");
        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == 0){
                TimeChoosenDialog.show(this, FightTimeDistanceManager.getInstance(this).getFightTimeDistance(), timeInMillis -> {
                    FightTimeDistanceManager.getInstance(this).setFightTimeDistance(timeInMillis);
                });
            }
            return true;
        });
        popupMenu.show();
    }
}
