package com.thopv.projects.ikariam.supports.utils;


import android.os.Handler;

/**
 * Created by thopv on 10/27/2017.
 */

public class MommentUtils {
    private static Handler handler = new Handler() ;
    public static void runBefore(long time, Runnable runnable){
        handler.postDelayed(runnable, time);
    }
    public static void runRepeat(int time, Runnable runnable){
        handler.post(() -> {
            runnable.run();
            handler.postDelayed(runnable, time);
        });
    }
    public static void cancel(Runnable runnable){
        handler.removeCallbacks(runnable);
    }
}
