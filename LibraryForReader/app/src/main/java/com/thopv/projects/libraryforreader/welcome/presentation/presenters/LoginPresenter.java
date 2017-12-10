package com.thopv.projects.libraryforreader.welcome.presentation.presenters;

import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Login;
import com.thopv.projects.libraryforreader.welcome.presentation.contracts.LoginContract;

/**
 * Created by jlaotsezu on 23/11/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private UseCaseHandler useCaseHandler;
    private Login login;
    private LoginContract.View view;
    public LoginPresenter(UseCaseHandler useCaseHandler, LoginContract.View view, Login login) {
        this.useCaseHandler = useCaseHandler;
        this.login = login;
        this.view = view;
    }

    @Override
    public void login() {
        String userName = view.getUserName();
        String password = view.getPassword();
        if(userName.isEmpty()){
            view.showUserNameError("Username unable null.");
        }
        else if(password.isEmpty()){
            view.showPasswordError("Password unable null.");
        }
        else{
            useCaseHandler.execute(login, new Login.RequestValues(userName, password), response -> {
                if(response.isSuccess()){
                    view.showMessage("Login success.");
                    view.startHomeView(response.getPayload().getUser());
                }
                else{
                    view.showWrongCridentialError("Operation failed. " + response.getMessage());
                }
            });
        }
    }
}
