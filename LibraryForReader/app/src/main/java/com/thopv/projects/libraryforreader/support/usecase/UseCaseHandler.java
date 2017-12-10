package com.thopv.projects.libraryforreader.support.usecase;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;

import junit.framework.Assert;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by thopv on 11/17/2017.
 */

public class UseCaseHandler {
    private static UseCaseHandler instance;
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());
    synchronized public static UseCaseHandler getInstance() {
        if(instance == null) {
            return new UseCaseHandler();
        }
        return instance;
    }
    public <Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> void execute(UseCase<Q, R> useCase, Q requestValues, UseCaseCallback<R> callback){
        Runner<Q, R> runner = new Runner<>(useCase, requestValues, callback);
        runner.run();
    }
    private class Runner<Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> implements UseCaseCallback<R>{
        private UseCase<Q, R> mUseCase;
        private UseCaseCallback<R> mCallback;
        public Runner(UseCase<Q, R> useCase, Q requestValues, UseCaseCallback<R> callback) {
            mUseCase = useCase;
            mUseCase.setRequestValues(requestValues);
            mUseCase.setCallback(this);
            mCallback = callback;
        }
        public void run(){
            executor.execute(() -> {
                mUseCase.run();
            });
        }
        @Override
        public void onCompleted(ComplexResponse<R> response) {
            handler.post(() -> {
                mCallback.onCompleted(response);
            });
        }
    }
}
