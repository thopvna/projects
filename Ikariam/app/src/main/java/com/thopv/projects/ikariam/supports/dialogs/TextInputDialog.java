package com.thopv.projects.ikariam.supports.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;

import com.thopv.projects.ikariam.R;

/**
 * Created by thopv on 10/29/2017.
 */

public class TextInputDialog {
    public interface Events{
        void onAccept(String input);
    }
    public static void show(Context context, String title, String hint, Events callback){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_text_input, null, false);
        TextInputEditText editText = view.findViewById(R.id.inputView);
        editText.setHint(hint);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("", null)
                .setNegativeButton("", null)
                .setCancelable(false)
                .create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Chấp nhận", (a,b) -> {
            a.cancel();
            callback.onAccept(editText.getText().toString());
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy bỏ", (a,b) -> {
            a.cancel();
        });
        alertDialog.show();
    }
}
