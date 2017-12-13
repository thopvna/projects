package com.thopv.projects.ikariam.home.presentation.views;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogDetail;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;

/**
 * Created by thopv on 10/31/2017.
 */

public class ArmyLogDetailView {
    private Context context;
    private AlertDialog dialog;
    private ArmyLogDetail logDetail;
    private View container;

    public ArmyLogDetailView(Context context, ArmyLogDetail logDetail){
        this.context = context;
        this.logDetail = logDetail;

        container = LayoutInflater.from(context).inflate(R.layout.send_army_log_detail, null , false);

        TextView logContentView = container.findViewById(R.id.logContentView);
        TextView sendTimeView = container.findViewById(R.id.sendTimeView);
        TextView arriveTimeView = container.findViewById(R.id.arriveTimeView);
        RecyclerView unitsListView = container.findViewById(R.id.unitsListView);

        logContentView.setText(logDetail.getContent());

        setupTimes(logDetail, sendTimeView, arriveTimeView);

        setupTroops(logDetail, unitsListView);

        dialog = new AlertDialog.Builder(context)
                .setTitle("Chi tiết ArmyLog")
                .setView(container)
                .setNegativeButton("Đóng", (a, b) -> {
                    a.cancel();
                })
                .setCancelable(false)
                .create();
    }

    private void setupTroops(ArmyLogDetail logDetail, RecyclerView unitsListView) {
        unitsListView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        unitsListView.setAdapter(new ShowUnitsAdapter(logDetail.getTroops()));
    }

    private void setupTimes(ArmyLogDetail logDetail, TextView sendTimeView, TextView arriveTimeView) {
        String startTime = "Vừa xong";
        try {
            startTime = DateUtils.getDistance(logDetail.getStartTime()) + "trước";
        } catch (Exception ignored) {}
        sendTimeView.setText(startTime);

        String endTime;
        if(logDetail.getType().equalsIgnoreCase(ArmyLogType.RETURN )|| logDetail.getType().equalsIgnoreCase(ArmyLogType.ATTACK) ){
            try {
                endTime = "Đến trong vòng " + DateUtils.getReverseDistance(logDetail.getEndTime()) + "nữa.";
            } catch (Exception ignored) {
                endTime = "Đã đến nơi vào lúc " + ignored.getMessage() + "trước";
            }
        }
        else{
            try {
                endTime = "Rã đông trong vòng " + DateUtils.getReverseDistance(logDetail.getEndTime()) + "nữa.";
            } catch (Exception ignored) {
                endTime = "Đã rã đông vào lúc " + ignored.getMessage() + "trước";
            }
        }
        arriveTimeView.setText(endTime);
    }

    public void show(){
        if(dialog != null && !dialog.isShowing())
            dialog.show();
    }

}
