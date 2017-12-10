package com.thopv.projects.libraryforreader.support.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.thopv.projects.libraryforreader.R;

/**
 * Created by thopv on 11/11/2017.
 */

public class DialogUtils {
    public interface ResultListener{
        void onResult(String result);
    }
    public static void showNumberInputDialog(Context context, ResultListener callback){
        View container = LayoutInflater.from(context).inflate(R.layout.dialog_text_input_content, null, false);
        EditText editText = container.findViewById(R.id.editText);
        editText.setHint("Nhập số lượng");
        new AlertDialog.Builder(context)
                .setTitle("Nhập số lượng")
                .setView(container)
                .setPositiveButton("Chấp nhận", (dialog, which) -> {
                    callback.onResult(editText.getText().toString());
                })
                .setNegativeButton("Hủy bỏ", (dialog, which) ->  {
                    dialog.cancel();
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
