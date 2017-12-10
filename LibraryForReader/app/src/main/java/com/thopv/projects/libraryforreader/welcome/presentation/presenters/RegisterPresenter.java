package com.thopv.projects.libraryforreader.welcome.presentation.presenters;

import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Register;
import com.thopv.projects.libraryforreader.welcome.presentation.contracts.RegisterContract;

/**
 * Created by jlaotsezu on 23/11/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private UseCaseHandler useCaseHandler;
    private Register register;
    private RegisterContract.View view;

    public RegisterPresenter(UseCaseHandler useCaseHandler, Register register, RegisterContract.View view) {
        this.useCaseHandler = useCaseHandler;
        this.register = register;
        this.view = view;
    }

    @Override
    public void register() {
        String userName = view.getUserName();
        String password = view.getPassword();
        String fullName = view.getFullName();
        String email = view.getEmail();
        String phone = view.getPhone();

        if(userName.isEmpty())
            view.showUserNameError("Username unable null.");
        else if(password.isEmpty())
            view.showPasswordError("Password unable null.");
        else{
            User user = new User(userName, password, fullName, email, phone, false);
            useCaseHandler.execute(register, new Register.RequestValues(user), response -> {
               if(response.isSuccess()){
                   view.showMessage("Register success.");
                   view.startLoginView(user);
               }
               else{
                   view.showError("Operation failed. " + response.getMessage());
               }
            });
        }
    }
}
