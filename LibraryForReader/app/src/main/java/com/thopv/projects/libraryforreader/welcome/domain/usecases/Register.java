package com.thopv.projects.libraryforreader.welcome.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

import junit.framework.Assert;

/**
 * Created by thopv on 12/2/2017.
 */
public class Register extends UseCase<Register.RequestValues, Register.ResponseValues> {
    private Repository<String, User> userRepository;

    public Register(Repository<String, User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        User user = requestValues.getUser();
        if(user == null){
            callback.onCompleted(ComplexResponse.fail("User unable empty."));
            return;
        }
        if(user.getUserName().isEmpty()){
            callback.onCompleted(ComplexResponse.fail("Username unable empty."));
            return;
        }
        if(user.getPassword().isEmpty()){
            callback.onCompleted(ComplexResponse.fail("Password unable empty."));
            return;
        }

        boolean success = userRepository.save(user);
        callback.onCompleted(ComplexResponse.get(success));
    }

    public static class RequestValues extends UseCase.RequestValues {
        private User user;

        public User getUser() {
            return user;
        }

        public RequestValues(User user) {
            this.user = user;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
