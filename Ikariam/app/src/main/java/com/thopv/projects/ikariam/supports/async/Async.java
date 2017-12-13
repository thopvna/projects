package com.thopv.projects.ikariam.supports.async;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.thopv.projects.ikariam.ComplexResponse;

/**
 * Created by thopv on 11/13/2017.
 */

public class Async {
    public interface InBackgroundRunnable<T>{
        @Nullable T run();
    }
    public interface OnPostRunnable<T>{
        void run(ComplexResponse<T> t);
    }
    public static <T> void execute(InBackgroundRunnable<T> inBackgroundRunnable, OnPostRunnable<T> onPostRunnable){
        MAsyncTask<T> asyncTask = new MAsyncTask<T>(inBackgroundRunnable, onPostRunnable);
        asyncTask.execute();
    }

    private static class MAsyncTask<T> extends AsyncTask<Void, Void, ComplexResponse<T>>{
        private InBackgroundRunnable<T> inBackgroundRunnable;
        private OnPostRunnable<T> onPostRunnable;

        public MAsyncTask(InBackgroundRunnable<T> inBackgroundRunnable, OnPostRunnable<T> onPostRunnable) {
            this.inBackgroundRunnable = inBackgroundRunnable;
            this.onPostRunnable = onPostRunnable;
        }

        @Override
        protected ComplexResponse<T> doInBackground(Void... voids) {
            try {
                return new ComplexResponse<>(true, inBackgroundRunnable.run());
            }
            catch (Exception e){
                for(int i =0; i <e.getStackTrace().length; i++){
                    Log.e("Async",e.getStackTrace()[i].toString());
                }
                return new ComplexResponse<>(false, e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(ComplexResponse<T> t) {
            if(onPostRunnable != null)
                onPostRunnable.run(t);
        }
    }
}
