package main.com.jlaotsezu.library.support.architecture;

import com.sun.istack.internal.NotNull;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.util.Assert;

/**
 * abstract class của các class nhằm thực hiện logic use case
 * @param <Q>
 * @param <R>
 */

public abstract class UseCase<Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> {

    private Q mRequestValues;
    private R mResponseValues;
    private @NotNull UseCaseCallback<R> mUseCaseCallback;

    public void setUseCaseCallback(UseCaseCallback<R> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    public UseCaseCallback<R> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void run(){
        Assert.notNull(mUseCaseCallback);
        executeUseCase(mRequestValues, getUseCaseCallback());
    }

    protected abstract void executeUseCase(Q requestValues, UseCaseCallback<R> callback);

    public void setRequestValues(Q mRequestValues) {
        this.mRequestValues = mRequestValues;
    }

    public R getResponseValues() {
        return mResponseValues;
    }

    public void setResponseValues(R mResponseValues) {
        this.mResponseValues = mResponseValues;
    }

    public interface UseCaseCallback<R>{
        void onCompleted(ComplexResponse<R> response);
    }

    public static class RequestValues{ }
    public static class ResponseValues{ }

}
