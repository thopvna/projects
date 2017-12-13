package com.thopv.projects.ikariam.supports.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by thopv on 10/29/2017.
 */

public class ConfirmDialog {
    public interface ConfirmDialogCallback{
        void onAccept();
    }
    public static void show(Context context, String title, String message, ConfirmDialogCallback callback){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("", null)
                .setNegativeButton("", null)
                .create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Chấp nhận", (a,b) -> {
            a.cancel();
            callback.onAccept();
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy bỏ", (a,b) -> {
            a.cancel();
        });
        alertDialog.show();
    }
}
