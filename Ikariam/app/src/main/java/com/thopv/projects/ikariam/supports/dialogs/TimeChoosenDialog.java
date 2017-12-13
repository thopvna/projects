package com.thopv.projects.ikariam.supports.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.supports.utils.DateUtils;

/**
 * Created by thopv on 11/15/2017.
 */

public class TimeChoosenDialog {
    public interface ChoosenListener{
        void onChoosen(long timeInMillis);
    }
    public static void show(Context context, ChoosenListener listener){
        View container = LayoutInflater.from(context).inflate(R.layout.dialog_time_choosen, null, false);
        TextView hourView = container.findViewById(R.id.hourView);
        TextView minuteView = container.findViewById(R.id.minuteView);
        TextView secondView = container.findViewById(R.id.secondView);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Lựa chọn thời gian di chuyển")
                .setView(container)
                .setCancelable(false)
                .setPositiveButton("Chấp nhận", (dialog, which) -> {
                    int hour = Integer.parseInt(hourView.getText().toString());
                    int minute = Integer.parseInt(minuteView.getText().toString());
                    int second = Integer.parseInt(secondView.getText().toString());
                    listener.onChoosen(hour * DateUtils.HOUR + minute * DateUtils.MINUTE + second * DateUtils.SECOND);
                    dialog.cancel();
                })
                .setNegativeButton("Hủy bỏ", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
        alertDialog.show();
    }
    public static void show(Context context, long oldTimeInMillis, ChoosenListener listener){
        View container = LayoutInflater.from(context).inflate(R.layout.dialog_time_choosen, null, false);
        TextView hourView = container.findViewById(R.id.hourView);
        TextView minuteView = container.findViewById(R.id.minuteView);
        TextView secondView = container.findViewById(R.id.secondView);

        DateUtils.parse(oldTimeInMillis, new DateUtils.ParseEvents() {
            @Override
            public void onParseSecond(long second) {
                secondView.setText(String.valueOf(second));
            }

            @Override
            public void onParseMinute(long minute) {
                minuteView.setText(String.valueOf(minute));
            }

            @Override
            public void onParseHour(long hour) {
                hourView.setText(String.valueOf(hour));
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Lựa chọn thời gian di chuyển")
                .setView(container)
                .setCancelable(false)
                .setPositiveButton("Chấp nhận", (dialog, which) -> {
                    int hour = Integer.parseInt(hourView.getText().toString());
                    int minute = Integer.parseInt(minuteView.getText().toString());
                    int second = Integer.parseInt(secondView.getText().toString());
                    listener.onChoosen(hour * DateUtils.HOUR + minute * DateUtils.MINUTE + second * DateUtils.SECOND);
                    dialog.cancel();
                })
                .setNegativeButton("Hủy bỏ", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
        alertDialog.show();
    }
}
