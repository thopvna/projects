package com.thopv.projects.ikariam;

import junit.framework.Assert;

/**
 * Created by thopv on 11/20/2017.
 */

public abstract  class UseCase<Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> {
    private Q requestValues;
    private R responseValues;
    private UseCaseCallback<R> useCaseCallback;

    public void run(){
        Assert.assertNotNull(useCaseCallback);
        executeUseCase(requestValues, useCaseCallback);
    }

    public void setRequestValues(Q requestValues) {
        this.requestValues = requestValues;
    }

    public void setUseCaseCallback(UseCaseCallback<R> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }

    public UseCaseCallback<R> getUseCaseCallback() {
        return useCaseCallback;
    }

    protected abstract void executeUseCase(Q requestValues, UseCaseCallback<R> callback);

    public interface UseCaseCallback<R extends UseCase.ResponseValues>{
        void onCompleted(ComplexResponse<R> response);
    }
    public static class RequestValues{}
    public static class ResponseValues{}
}
