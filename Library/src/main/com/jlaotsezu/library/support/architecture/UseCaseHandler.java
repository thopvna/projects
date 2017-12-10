package main.com.jlaotsezu.library.support.architecture;

import com.sun.istack.internal.NotNull;
import javafx.application.Platform;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class này dùng để runCUD các use case.
 */

public class UseCaseHandler {
    private static Executor executor = Executors.newSingleThreadExecutor();

    public <Q extends UseCase.RequestValues,R extends UseCase.ResponseValues> void execute(UseCase<Q ,R > useCase, Q requestValues, @NotNull UseCase.UseCaseCallback<R> callback){
        Runner<Q, R> runner = new Runner<>(useCase, requestValues, callback);
        runner.run();
    }
    private static class Runner<Q extends UseCase.RequestValues, R extends UseCase.ResponseValues> implements UseCase.UseCaseCallback<R>{
        private UseCase<Q, R> useCase;
        private UseCase.UseCaseCallback<R> callback;

        public Runner(UseCase<Q, R> useCase, Q requestValues, UseCase.UseCaseCallback<R> callback) {
            this.useCase = useCase;
            this.useCase.setRequestValues(requestValues);
            this.useCase.setUseCaseCallback(this);
            this.callback = callback;
        }

        public void run(){
            executor.execute(() -> {
                useCase.run();
            });
        }
        @Override
        public void onCompleted(ComplexResponse<R> response) {
            Platform.runLater(() -> {
                callback.onCompleted(response);
            });
        }
    }
}
