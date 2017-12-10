package com.thopv.projects.libraryforreader.support.utils;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by thopv on 12/2/2017.
 */

public class ToastUtils {
    private Toast toast;
    private WeakReference<Context> context;
    private static ToastUtils instance;
    public ToastUtils(Context context){
        this.context = new WeakReference<>(context);
    }
    public static ToastUtils getInstance(Context context){
        if(instance == null){
            instance = new ToastUtils(context);
        }
        return instance;
    }
    public void showMessage(String message){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context.get(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
