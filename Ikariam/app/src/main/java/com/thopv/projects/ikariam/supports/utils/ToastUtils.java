package com.thopv.projects.ikariam.supports.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by thopv on 11/14/2017.
 */

public class ToastUtils {

    private static ToastUtils instance;
    private Toast toast;
    private Context context;
    private ToastUtils(Context context){
        this.context = context.getApplicationContext();
    }
    public static ToastUtils getInstance(Context context) {
        if(instance == null)
            instance = new ToastUtils(context.getApplicationContext());
        return instance;
    }
    public void show(String mess){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);
        toast.show();
    }
}
