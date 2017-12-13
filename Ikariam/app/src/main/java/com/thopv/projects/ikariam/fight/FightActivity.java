package com.thopv.projects.ikariam.fight;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.adapters.RoundedCornersTransform;
import com.thopv.projects.ikariam.supports.dialogs.ConfirmDialog;
import com.thopv.projects.ikariam.supports.dialogs.TimeChoosenDialog;
import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.supports.utils.ToastUtils;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.fight.presentation.view_models.FightStatusViewModel;
import com.thopv.projects.ikariam.fight.presentation.view_models.LoEffectViewModel;
import com.thopv.projects.ikariam.fight.presentation.views.UnitsAmountSelectAdapter;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.models.ModelFieldTroop;
import com.thopv.projects.ikariam.fight.presentation.contracts.FightContract;
import com.thopv.projects.ikariam.fight.presentation.view_models.AttackTroopsViewModel;
import com.thopv.projects.ikariam.fight.presentation.view_models.FieldTroopsViewModel;
import com.thopv.projects.ikariam.fight.presentation.view_models.HomeTroopsViewModel;
import com.thopv.projects.ikariam.fight.presentation.presenters.FightPresenter;
import com.thopv.projects.ikariam.fight.presentation.views.ReverseTroopsAdapter;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitAttributesDialog;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;
import com.thopv.projects.ikariam.servers.FightTimeDistanceManager;

import junit.framework.Assert;

import java.util.List;
import java.util.Map;

public class FightActivity extends AppCompatActivity implements FightContract.Controller {
    DrawerLayout drawerLayout;
    FightPresenter fightPresenter;
    private ReverseTroopsAdapter attackReverseTroopsAdapter;
    private ReverseTroopsAdapter homeReverseTroopsAdapter;
    private TextView fightInformView;
    private TextView turnInformView;
    private Runnable timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Assert.assertNotNull(getIntent());
        Assert.assertNotNull(getIntent().getExtras());
        Assert.assertNotNull(getIntent().getExtras().getString("house"));

        House house = new Gson().fromJson(getIntent().getExtras().getString("house"), House.class);

        int shipyardLevel = house.getShipyard().getLevel();
        int portLevel = house.getPort().getLevel();

        chooseBattleField(shipyardLevel, portLevel);

        drawerLayout =  findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(Gravity.START);

        setupReverseView();

        initPresenter(house);
    }

    private void setupReverseView() {
        RecyclerView attackReverseTroopsView = findViewById(R.id.attackReverse);
        RecyclerView homeTroopsView = findViewById(R.id.defReverse);

        attackReverseTroopsView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        homeTroopsView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        attackReverseTroopsAdapter = new ReverseTroopsAdapter();
        homeReverseTroopsAdapter = new ReverseTroopsAdapter();

        attackReverseTroopsView.setAdapter(attackReverseTroopsAdapter);
        homeTroopsView.setAdapter(homeReverseTroopsAdapter);
    }

    private void initPresenter(House house) {
        FieldTroopsViewModel fieldTroopsViewModel = ViewModelProviders.of(this).get(FieldTroopsViewModel.class);
        AttackTroopsViewModel attackTroopsViewModel = ViewModelProviders.of(this).get(AttackTroopsViewModel.class);
        HomeTroopsViewModel homeTroopsViewModel = ViewModelProviders.of(this).get(HomeTroopsViewModel.class);
        LoEffectViewModel loEffectViewModel = ViewModelProviders.of(this).get(LoEffectViewModel.class);
        FightStatusViewModel fightStatusViewModel = ViewModelProviders.of(this).get(FightStatusViewModel.class);
        //TODO: Có bộ đếm ngược thời gian thì ngon
        UseCaseComponent useCaseComponent = UseCaseComponent.getInstance(this);

        fightPresenter = new FightPresenter(this,
                house, fieldTroopsViewModel,
                attackTroopsViewModel, homeTroopsViewModel,
                loEffectViewModel,
                fightStatusViewModel,
                useCaseComponent.getReturnUnits(),
                useCaseComponent.getLoadAttackUnits()
        );
    }

    private void chooseBattleField(int shipyardLevel, int portLevel) {
        int max = shipyardLevel > portLevel ? shipyardLevel : portLevel;
        if (max < 8) {
            setContentView(R.layout.activity_sea_battle_field_0_7);
        } else if (max < 15) {
            setContentView(R.layout.activity_sea_battle_field_8_14);
        } else if (max < 22) {
            setContentView(R.layout.activity_sea_battle_field_15_21);
        } else if (max < 29) {
            setContentView(R.layout.activity_sea_battle_field_22_28);
        } else {
            setContentView(R.layout.activity_sea_battle_field_29);
        }
    }

    @Override
    public void showAttackOptionsMenu() {
        View view = findViewById(R.id.attackOptions);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            showOptionsMenu(v);
        });
    }

    private void showOptionsMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu = popupMenu.getMenu();
        menu.add(0, 0, 0, "Gửi lính");
        menu.add(0, 1, 1, "Rút lính");
        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == 0){
                fightPresenter.sendUnits();
            }
            else if(item.getItemId() == 1){
                fightPresenter.returnUnits();
            }
            return true;
        });
        popupMenu.show();
    }
    public void showUnitsReturn(List<BaseTroop> troops){
        View view = getLayoutInflater().inflate(R.layout.units_select_with_seekbar, null, false);
        UnitsAmountSelectAdapter adapter = new UnitsAmountSelectAdapter(troops);
        RecyclerView listView = view.findViewById(R.id.unitAmountSelectionListView);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Rút quân")
                .setView(view)
                .setNegativeButton("Hủy bỏ", null)
                .setCancelable(false)
                .setPositiveButton("Chấp nhận", (dialg, which) -> {
                    confirmReturnUnits(adapter.getUnitsAmount());
                })
                .create();
        dialog.show();
    }
    private void confirmReturnUnits(Map<String, Integer> unitsAmount){
        TimeChoosenDialog.show(this, timeInMillis -> {
            ConfirmDialog.show(this, "Xác nhận", "Bạn chắc chắn muốn rút ?" , () -> {
                long distance = FightTimeDistanceManager.getInstance(this).getFightTimeDistance();
                fightPresenter.returnUnits(unitsAmount, timeInMillis, distance);
                FightTimeDistanceManager.getInstance(this).addObserver((o, arg) -> {
                    if(arg instanceof Long){
                        fightPresenter.changeFightTimeDistance((Long) arg);
                    }
                });
            });
        });
    }
    public void showUnitsSend(House house){
        Intent intent = new Intent(this, UnitsSendActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("house", new Gson().toJson(house));
        startActivity(intent);
    }

    @Override
    public void showDefLoEffect() {
        findViewById(R.id.defLoEffectIcon).setVisibility(View.VISIBLE);
    }

    @Override
    public void showAttackLoEffect() {
        findViewById(R.id.attackLoEffectIcon).setVisibility(View.VISIBLE);
    }


    @Override
    public void showError(String err) {
        ToastUtils.getInstance(this).show(err);
    }

    @Override
    public void hideAttackOptionsMenu() {
        findViewById(R.id.attackOptions).setVisibility(View.GONE);
    }

    @Override
    public void hideDefOptionsMenu() {
        findViewById(R.id.defOptions).setVisibility(View.GONE);
    }

    @Override
    public void showFightStatus(boolean isFighting, long startTime, int turn) {
        fightInformView = findViewById(R.id.fightInformView);
        turnInformView = findViewById(R.id.turnInformView);
        String fightInform;
        try {
            fightInform = isFighting ? "Trận đấu diễn ra: " + DateUtils.getDistance(startTime) + "trước" : "Trận đấu chưa xảy ra(hoặc đã kết thúc)";
        } catch (Exception e) {
            fightInform = "Trận đấu vừa diễn ra";
        }
        String turnInform = isFighting ? "Hiệp số " + turn : "Kết thúc tại hiệp số " + turn;
        fightInformView.setText(fightInform);
        turnInformView.setText(turnInform);

        showError(turnInform);

        if(timeCount != null)
            handler.removeCallbacks(timeCount);

        if(isFighting){
            timeCount = new Runnable() {
                long count = FightTimeDistanceManager.getInstance(FightActivity.this).getFightTimeDistance() / 1000;
                @Override
                public void run() {
                    count--;
                    if(count > 0 && count % 3 == 0) {
                        showError("" + count);
                        handler.postDelayed(this, 1000);
                    }
                    else
                        handler.removeCallbacks(this);
                }
            };
            handler.postDelayed(timeCount, 1000);
        }
        else{
            showError("Trận đấu đã kết thúc.");
        }
    }
    private static Handler handler = new Handler();

    @Override
    public void showFightingTroops(List<ModelFieldTroop> troops) {
        Log.e(getClass().getSimpleName(), "Model Field Troop size = " + troops.size());
        for(ModelFieldTroop fieldTroop : troops){
            if(fieldTroop.isAlive())
                showFightingTroop(fieldTroop);
            else
                clearShipView(fieldTroop.getViewId());
        }
    }
    private void clearShipView(int viewId){
        View view = findViewById(viewId);
        ImageView unitIconView =  view.findViewById(R.id.unitIcon);
        TextView unitAmountView =  view.findViewById(R.id.unitAmount);
        ProgressBar unitMunitionView = view.findViewById(R.id.unitMunition);
        ProgressBar unitHpView = view.findViewById(R.id.unitHp);

        unitIconView.setImageDrawable(getResources().getDrawable(R.color.super_gray));
        unitAmountView.setText("0/0");
        unitHpView.setProgress(0);
        unitMunitionView.setProgress(0);
    }
    public void showFightingTroop(ModelFieldTroop fieldTroop){
        String unitName = fieldTroop.getUnitName();
        Unit unit = UnitFactory.getUnit(unitName);
        View view = findViewById(fieldTroop.getViewId());

        ImageView unitIconView =  view.findViewById(R.id.unitIcon);
        TextView unitAmountView =  view.findViewById(R.id.unitAmount);
        ProgressBar unitMunitionView = view.findViewById(R.id.unitMunition);
        ProgressBar unitHpView = view.findViewById(R.id.unitHp);

        Picasso.with(this)
                .load(unit.getDrawable())
                .resizeDimen(R.dimen.unit_cardview_width, R.dimen.unit_cardview_small_height)
                .transform(new RoundedCornersTransform(4,4))
                .into(unitIconView);

        unitHpView.setMax(fieldTroop.getMaxHitpoints());
        unitMunitionView.setMax(fieldTroop.getMaxMunitions() * 100);

        animateChangeHpAndMana(fieldTroop, unitMunitionView, unitHpView);

        int maxAmount = fieldTroop.getMaxAmount();
        String unitAmount = fieldTroop.getAmount() + "/" + maxAmount;
        unitAmountView.setText(unitAmount);

        view.setOnClickListener((v) -> {
            ViewUtils.disableView(v);
            showUnitAttribute(unitName);

            Log.e(getClass().getSimpleName(), "fieldTroop: " + unitName + ": munitions" + fieldTroop.getCurrentMunitions() + "/" + fieldTroop.getMaxMunitions());
        });
    }

    private void animateChangeHpAndMana(ModelFieldTroop fieldTroop, ProgressBar unitMunitionView, ProgressBar unitHpView) {
        ObjectAnimator hpAnimator = ObjectAnimator.ofInt(unitHpView, "progress", unitHpView.getProgress(),fieldTroop.getCurrentHitPoints());
        ObjectAnimator manaAnimator = ObjectAnimator.ofInt(unitMunitionView, "progress", unitMunitionView.getProgress(), fieldTroop.getCurrentMunitions() * 100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(hpAnimator, manaAnimator);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void showUnitAttribute(String unitName){
        UnitAttributesDialog dialog = new UnitAttributesDialog(this, unitName);
        dialog.show();
    }

    @Override
    public void showAttackReverseTroops(List<BaseTroop> troops) {
        attackReverseTroopsAdapter.setTroops(troops);
    }

    @Override
    public void showHomeTroops(List<BaseTroop> homeTroops) {
        homeReverseTroopsAdapter.setTroops(homeTroops);
    }
    @Override
    public void showExtraAttackReverseTroops(List<BaseTroop> troops) {
        attackReverseTroopsAdapter.setExtraTroops(troops);
    }

    @Override
    public void showExtraHomeTroops(List<BaseTroop> homeTroops) {
        homeReverseTroopsAdapter.setExtraTroops(homeTroops);
    }
}
