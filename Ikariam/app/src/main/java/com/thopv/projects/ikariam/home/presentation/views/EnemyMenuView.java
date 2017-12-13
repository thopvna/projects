package com.thopv.projects.ikariam.home.presentation.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.supports.utils.ToastUtils;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.home.presentation.contracts.EnemyMenuContract;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.presenters.EnemyMenuPresenter;

import java.util.List;

/**
 * Created by thopv on 10/11/2017.
 */

public class EnemyMenuView implements EnemyMenuContract.Controller{
    private AlertDialog alertDialog = null;
    private View contentContainer = null;
    private TextView attackButton, showLogsButton, moreOptionsButton;
    private TextView townHallLevelView, shipyardLevelView, portLevelView, totalUnitsAmountView, effectsValueView;
    private EnemyMenuPresenter enemyMenuPresenter;
    private Context context;

    public interface AttackEvents {
        void onAttackButtonClicked();
    }
    public EnemyMenuView(Context context, House house, AttackEvents attackEvents){
        this.context = context;
        contentContainer = LayoutInflater.from(context).inflate(R.layout.enemy_house_control_menu_content, null, false);
        attackButton =  contentContainer.findViewById(R.id.attackButton);
        showLogsButton =  contentContainer.findViewById(R.id.setupUnitsButton);
        moreOptionsButton =  contentContainer.findViewById(R.id.moreOptionsButton);

        townHallLevelView =  contentContainer.findViewById(R.id.townHallLevelValue);
        shipyardLevelView =  contentContainer.findViewById(R.id.shipyardLevelValue);
        portLevelView =  contentContainer.findViewById(R.id.portLevelValue);
        totalUnitsAmountView =  contentContainer.findViewById(R.id.totalUnitsAmountValue);
        effectsValueView =  contentContainer.findViewById(R.id.effectsValue);

        totalUnitsAmountView.setText("XXX");

        String townHallLevelString = "Cấp " + house.getTownHall().getLevel();
        String shipyardLevelString = "Cấp " + house.getShipyard().getLevel();
        String portLevelString =  "Cấp " + house.getPort().getLevel();


        attackButton.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            attackEvents.onAttackButtonClicked();
        });

        showLogsButton.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            //TODO: Triển khai show log
        });


        townHallLevelView.setText(townHallLevelString);
        shipyardLevelView.setText(shipyardLevelString);
        portLevelView.setText(portLevelString);

        alertDialog = new AlertDialog.Builder(context)
                .setTitle(house.isBlue() ? "Blue team" : "Red team")
                .setView(contentContainer)
                .setPositiveButton("", null)
                .setNeutralButton("", null)
                .setCancelable(false)
                .create();

        UseCaseComponent useCaseComponent = UseCaseComponent.getInstance(context);

        enemyMenuPresenter = new EnemyMenuPresenter(this, house,
                useCaseComponent.getGetTroopCount(),
                useCaseComponent.getAddHomeTroops(),
                useCaseComponent.getRemoveHouse(),
                useCaseComponent.getApplyLoEffect(),
                useCaseComponent.getApplyWhaleEffect(),
                useCaseComponent.getLoadEffectStatus(),
                useCaseComponent.getLoadHomeTroops(),
                useCaseComponent.getClearUnits(),
                useCaseComponent.getApplyCollosusEffect());

        moreOptionsButton.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            PopupMenu popupMenu = new PopupMenu(context, v);
            Menu menu = popupMenu.getMenu();
            menu.add(0, 0, 0, "Thiết lập lính");
            menu.add(0, 1, 1, "Kích hoạt lò");
            menu.add(0, 3, 3, "Kích hoạt Collosus");
            menu.add(0, 4, 5, "Xoá nhà");
            menu.add(0, 5, 4, "Clear lính");

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == 0){
                    enemyMenuPresenter.loadHomeTroops();
                }
                else if(item.getItemId() == 1){
                    enemyMenuPresenter.applyLoEffect();
                }
                else if(item.getItemId() == 2){
                    enemyMenuPresenter.applyWhaleEffect();
                }
                else if(item.getItemId() == 3){
                    enemyMenuPresenter.applyCollosusEffect();
                }
                else if(item.getItemId() == 4){
                    enemyMenuPresenter.removeHouse();
                }
                else if(item.getItemId() == 5){
                    enemyMenuPresenter.clearUnits();
                }
                return true;
            });
            popupMenu.show();
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ĐÓNG MENU", (dialog, which)
                -> dialog.cancel());
    }
    public void show(){
        if(!alertDialog.isShowing())
            alertDialog.show();
    }
    @Override
    public void showUnitsAmount(int unitsAmount) {
        totalUnitsAmountView.setText(String.valueOf(unitsAmount));
    }


    @Override
    public void showEffectStatus(String status) {
        effectsValueView.setText(status);
    }


    @Override
    public void showUnitsSetup(List<BaseTroop> modelTroops) {
        UnitsAMountSetupView unitsAMountSetupView = new UnitsAMountSetupView("Thiết lập số lượng lính.", context, troops -> {
            enemyMenuPresenter.setupUnits(troops);
        });
        unitsAMountSetupView.setData(modelTroops);
        unitsAMountSetupView.show();
    }

    @Override
    public void showError(String err) {
        ToastUtils.getInstance(context).show(err);
    }

    @Override
    public void cancel() {
        if(alertDialog != null && alertDialog.isShowing())
            alertDialog.cancel();
    }

}
