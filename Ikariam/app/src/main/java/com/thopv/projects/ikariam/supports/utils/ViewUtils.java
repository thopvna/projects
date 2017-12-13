package com.thopv.projects.ikariam.supports.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thopv on 11/13/2017.
 */

public class ViewUtils {
    public static void disableView(View v, long timeInMillis){
        v.setClickable(false);
        MommentUtils.runBefore(timeInMillis, () -> {
            v.setClickable(true);
        });
    }
    public static void disableView(View v){
        v.setClickable(false);
        MommentUtils.runBefore(1000, () -> {
            v.setClickable(true);
        });
    }
    public static class TextViewUtils{
        private static Handler handler = new Handler();
        private static Map<Integer, Runnable> viewsRunnables = new HashMap<>();
        public static void setTextBefore(long timeInMillis, String text, TextView view){
            if(viewsRunnables.keySet().contains(view.getId())){
                Runnable runnable = viewsRunnables.get(view.getId());
                handler.removeCallbacks(runnable);
            }
            Runnable runnable = () -> {
                view.setText(text);
            };

            viewsRunnables.put(view.getId(), runnable);
            handler.postDelayed(runnable, timeInMillis);
        }
    }
    public static void slowShow(View view){
        AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
    public interface OnStartListener{
        void onStart();
    }
    public interface OnCompletedListener{
        void onCompleted();
    }
    public static void flipFlopView(View view, OnStartListener onStartListener, OnCompletedListener onCompletedListener){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1);
        objectAnimator.setDuration(200);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onStartListener.onStart();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                onCompletedListener.onCompleted();
            }
        });
        objectAnimator.start();
    }

}
