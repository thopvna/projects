package com.thopv.projects.ikariam;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by thopv on 11/20/2017.
 */

public class UseCaseHandler {
    public static <Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> void execute(UseCase<Q,R> useCase, Q requestValues, UseCase.UseCaseCallback<R> useCaseCallback){
        UseCaseRunner runner = new UseCaseRunner<>(useCase, requestValues, useCaseCallback);
        runner.run();
    }
    private static class UseCaseRunner<Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> implements UseCase.UseCaseCallback<R> {
        private UseCase.UseCaseCallback<R> mCallback;
        private UseCase<Q, R> mUseCase;
        public UseCaseRunner(UseCase<Q, R> useCase, Q requestValues, UseCase.UseCaseCallback<R> callback) {
            this.mCallback = callback;
            this.mUseCase = useCase;
            mUseCase.setRequestValues(requestValues);
            mUseCase.setUseCaseCallback(this);
        }
        public void run(){
            executor.execute(() -> mUseCase.run());
        }
        @Override
        public void onCompleted(ComplexResponse<R> response) {
            handler.post(() -> {
                mCallback.onCompleted(response);
            });
        }
    }
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());
}
