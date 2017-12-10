package com.thopv.projects.libraryforreader.welcome.presentation.contracts;


import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

/**
 * Created by jlaotsezu on 23/11/2017.
 */

public interface LoginContract {
    interface View {
        void showMessage(String message);
        void startHomeView(User user);
        void showUserNameError(String message);
        void showPasswordError(String message);
        String getUserName();
        String getPassword();
        void startRegisterView();
        void showWrongCridentialError(String message);
    }
    interface Presenter{
        void login();
    }
}
