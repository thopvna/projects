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
public class Login extends UseCase<Login.RequestValues, Login.ResponseValues> {
    private Repository<String, User> userRepository;

    public Login(Repository<String, User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String userName = requestValues.getUserName();
        String password = requestValues.getPassword();

        if(userName == null || userName.isEmpty()){
            callback.onCompleted(ComplexResponse.fail("Username unable empty."));
            return;
        }
        if(password == null || password.isEmpty()){
            callback.onCompleted(ComplexResponse.fail("Password unable empty."));
            return;
        }

        User user = userRepository.findById(userName);

        if(user != null){
            if(user.getPassword().equalsIgnoreCase(password)){
                callback.onCompleted(ComplexResponse.success(new ResponseValues(user)));
            }
            else{
                callback.onCompleted(ComplexResponse.fail("Password is wrong."));
            }
        }
        else{
            callback.onCompleted(ComplexResponse.fail("User is not existing."));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private String userName;
        private String password;

        public RequestValues(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private User user;

        public ResponseValues(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}
