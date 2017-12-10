package com.thopv.projects.libraryforreader.support.usecase;

import junit.framework.Assert;

/**
 * Created by thopv on 11/17/2017.
 */

public abstract class UseCase<Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> {

    private Q requestValues;
    private R responseValues;
    private UseCaseCallback<R> callback;

    public Q getRequestValues() {
        return requestValues;
    }

    public void setRequestValues(Q requestValues) {
        this.requestValues = requestValues;
    }

    public R getResponseValues() {
        return responseValues;
    }

    public void setResponseValues(R responseValues) {
        this.responseValues = responseValues;
    }

    public void run(){
        Assert.assertNotNull(requestValues);
        Assert.assertNotNull(callback);
        executeUseCase(requestValues, callback);
    }

    protected abstract void executeUseCase(Q requestValues, UseCaseCallback<R> caseCallback);

    public UseCaseCallback<R> getCallback() {
        return callback;
    }

    public void setCallback(UseCaseCallback<R> callback) {
        this.callback = callback;
    }

    public static class RequestValues{

    }
    public static class ResponseValues{

    }
}
