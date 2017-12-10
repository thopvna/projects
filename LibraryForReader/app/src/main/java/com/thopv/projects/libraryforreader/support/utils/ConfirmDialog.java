package com.thopv.projects.libraryforreader.support.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by thopv on 12/4/2017.
 */

public class ConfirmDialog {
    public interface Events{
        void onConfirm();
    }
    public static void show(Context context, String title, String message, Events events){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Hủy bỏ", null)
                .setCancelable(false)
                .setPositiveButton("Chấp nhận", (dialog1, which) -> {
                    events.onConfirm();
                })
                .create();
        dialog.show();
    }
}
