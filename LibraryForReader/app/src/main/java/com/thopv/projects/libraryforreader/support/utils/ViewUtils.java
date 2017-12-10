package com.thopv.projects.libraryforreader.support.utils;

import android.view.View;

/**
 * Created by thopv on 11/9/2017.
 */

public class ViewUtils {
    public static void disableView(View view){
        view.setClickable(false);
        MommentUtils.runBefore(1500, () -> {
            view.setClickable(true);
        });
    }
}
