package com.thopv.projects.ikariam.home.presentation.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.supports.utils.ToastUtils;
import com.thopv.projects.ikariam.supports.utils.ViewUtils;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogDetail;
import com.thopv.projects.ikariam.fight.FightActivity;
import com.thopv.projects.ikariam.home.presentation.contracts.MeMenuContract;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.presenters.MeMenuPresenter;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.config.UseCaseComponent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 10/11/2017.
 */

public class MeMenuView implements MeMenuContract.Controller {
    private AlertDialog alertDialog = null;
    private View container = null;
    private TextView moreOptionsButton, showUnitsButton, showLogButton, showFieldButton;
    private TextView townHallLevelView, shipyardLevelView, portLevelView, totalUnitsAmountView, effectsView;
    private Context context;
    private MeMenuPresenter presenter;

    public void showHouse(House house){
        String townHallLevelString = "Cấp " + house.getTownHall().getLevel();
        String shipyardLevelString = "Cấp " + house.getShipyard().getLevel();
        String portLevelString =  "Cấp " + house.getPort().getLevel();

        townHallLevelView.setText(townHallLevelString);
        shipyardLevelView.setText(shipyardLevelString);
        portLevelView.setText(portLevelString);

        alertDialog = new AlertDialog.Builder(context)
                .setTitle(house.getParty() == 0 ? "Blue Team" : "Red Team")
                .setView(container)
                .setPositiveButton("", null)
                .setNeutralButton("", null)
                .setCancelable(false)
                .create();

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ĐÓNG MENU", (dialog, which)
                -> dialog.cancel());
    }

    @Override
    public void showUnitsAmount(int unitsAmount) {
        String totalUnits = unitsAmount + "";
        totalUnitsAmountView.setText(totalUnits);
    }

    @Override
    public void showUnits(List<HomeTroop> homeTroops) {
        View unitsContainer = LayoutInflater.from(container.getContext()).inflate(R.layout.dialog_show_units, null, false);
        RecyclerView listView = unitsContainer.findViewById(R.id.unitsListView);
        listView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        listView.setAdapter(ShowUnitsAdapter.createByTroops(homeTroops));
        AlertDialog dialog = new AlertDialog.Builder(container.getContext())
                .setTitle("Lính của bạn")
                .setView(unitsContainer)
                .setNegativeButton("Đóng", (idialog, which) -> idialog.dismiss())
                .create();
        dialog.show();
    }

    @Override
    public void showEffectStatus(String status) {
        effectsView.setText(status);
    }

    @Override
    public void showBattleField(House house) {
        Intent intent = new Intent(context, FightActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("house", new Gson().toJson(house));
        context.startActivity(intent);
    }

    public void showUnitsSetup(List<BaseTroop> baseTroops) {
        UnitsAMountSetupView unitsAMountSetupView = new UnitsAMountSetupView("Thiết lập lính", container.getContext(), troops -> {
            presenter.setupUnits(troops);
        });
        unitsAMountSetupView.setData(baseTroops);
        unitsAMountSetupView.show();
    }

    @Override
    public void showLogs(List<ArmyLog> logs) {
        View logContainer = LayoutInflater.from(context).inflate(R.layout.dialog_send_army_logs, null, false);
        RecyclerView listView = logContainer.findViewById(R.id.logsListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        listView.setLayoutManager(layoutManager);
        ArmyLogAdapter adapter = new ArmyLogAdapter(logs);
        adapter.setItemClickListener((log) -> {
            presenter.loadDetailLog(log);
        });
        listView.setAdapter(adapter);
        AlertDialog logDialog = new AlertDialog.Builder(context)
                .setTitle("Logs")
                .setView(logContainer)
                .setCancelable(false)
                .setNegativeButton("Đóng",(dialog, which) -> dialog.dismiss())
                .create();
        logDialog.show();
    }

    @Override
    public void showLogDetail(ArmyLogDetail log) {
        ArmyLogDetailView detailView = new ArmyLogDetailView(context, log);
        detailView.show();
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

    public MeMenuView(Context context, House house){
        this.context = context;
        UseCaseComponent useCaseComponent = UseCaseComponent.getInstance(context);
        this.presenter = new MeMenuPresenter(this,
                house,
                useCaseComponent.getGetTroopCount(),
                useCaseComponent.getAddHomeTroops(),
                useCaseComponent.getLoadLogs(),
                useCaseComponent.getGetLogDetail(),
                useCaseComponent.getRemoveHouse(),
                useCaseComponent.getLoadHomeTroops(),
                useCaseComponent.getApplyLoEffect(),
                useCaseComponent.getApplyWhaleEffect(),
                useCaseComponent.getLoadEffectStatus(),
                useCaseComponent.getClearUnits());
        container = LayoutInflater.from(context).inflate(R.layout.me_house_control_menu_content, null, false);
        showUnitsButton =  container.findViewById(R.id.showUnitsButton);
        showLogButton =  container.findViewById(R.id.showLogButton);
        showFieldButton =  container.findViewById(R.id.showFieldButton);
        moreOptionsButton =  container.findViewById(R.id.moreOptionsButton);
        townHallLevelView =  container.findViewById(R.id.townHallLevelValue);
        shipyardLevelView =  container.findViewById(R.id.shipyardLevelValue);
        portLevelView =  container.findViewById(R.id.portLevelValue);
        totalUnitsAmountView =  container.findViewById(R.id.totalUnitsAmountValue);
        effectsView =  container.findViewById(R.id.effectsValue);

        showUnitsButton.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            presenter.loadTroops();
        });

        showLogButton.setOnClickListener((v) -> {
            ViewUtils.disableView(v);
            presenter.loadLogs();
        });

        showFieldButton.setOnClickListener((v) -> {
            ViewUtils.disableView(v);
            presenter.loadBattleField();
        });

        moreOptionsButton.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            PopupMenu popupMenu = new PopupMenu(context, v);
            Menu menu = popupMenu.getMenu();
            menu.add(0, 0, 0, "Thiết lập lính");
            menu.add(0, 1, 1, "Kích hoạt lò");
            menu.add(0, 2, 2, "Kích hoạt whale");
            menu.add(0, 4, 5, "Xoá nhà");
            menu.add(0, 5, 4, "Clear lính");

            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == 0){
                    presenter.loadHomeTroops();
                }
                else if(item.getItemId() == 1){
                    presenter.applyLoEffect();
                }
                else if(item.getItemId() == 2){
                    presenter.applyWhaleEffect();
                }
                else if(item.getItemId() == 3){

                }
                else if(item.getItemId() == 4){
                    presenter.removeHouse();
                }
                else if(item.getItemId() == 5){
                    presenter.clearUnits();
                }
                return true;
            });
            popupMenu.show();
        });

        showHouse(house);
    }

    public void show(){
        if(alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    /**
     * Created by thopv on 10/30/2017.
     */


    private interface LogsItemClickListener{
        void onClicked(ArmyLog log);
    }
    public class ArmyLogAdapter extends RecyclerView.Adapter<ArmyLogAdapter.ArmyLogViewHolder> {
        private List<ArmyLog> logs;

        public ArmyLogAdapter(List<ArmyLog> logs) {
            this.logs = logs != null ? logs : new LinkedList<>();
        }

        @Override
        public ArmyLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_army_log_cardview, parent, false);
            return new ArmyLogViewHolder(view);
        }
        private LogsItemClickListener itemClickListener;

        public void setItemClickListener(LogsItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onBindViewHolder(ArmyLogViewHolder holder, int position) {
            ArmyLog log = logs.get(position);
            holder.setSendArmyLog(log, itemClickListener);
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
        public class ArmyLogViewHolder extends RecyclerView.ViewHolder {
            private View container;
            private TextView logSendTimeView, logContentView, detailButton;
            public ArmyLogViewHolder(View itemView) {
                super(itemView);
                container = itemView;
                logSendTimeView = container.findViewById(R.id.logSendTime);
                logContentView = container.findViewById(R.id.logContent);
                detailButton = container.findViewById(R.id.detailButton);
            }

            public void setSendArmyLog(ArmyLog log, LogsItemClickListener callback){
                String logContent = log.getContent();
                logContentView.setText(logContent);
                String logSendTime = "Vừa xong";
                try {
                    logSendTime = DateUtils.getDistance(log.getStartTime()) + "trước";
                } catch (Exception ignored) {}
                logSendTimeView.setText(logSendTime);
                String detailLabel = "Nhấp xem chi tiết.";
                detailButton.setText(detailLabel);
                container.setOnClickListener((v) -> {
                    ViewUtils.disableView(v);
                    if(callback != null)
                        callback.onClicked(log);
                });
            }
        }
    }
}
