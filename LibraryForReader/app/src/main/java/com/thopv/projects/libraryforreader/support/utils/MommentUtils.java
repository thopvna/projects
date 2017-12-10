package com.thopv.projects.libraryforreader.support.utils;

import android.os.Handler;

/**
 * Created by thopv on 11/9/2017.
 */

public class MommentUtils {
    private static Handler handler = new Handler();
    public static void runBefore(long time, Runnable runnable){
        handler.postDelayed(runnable, time);
    }
}
