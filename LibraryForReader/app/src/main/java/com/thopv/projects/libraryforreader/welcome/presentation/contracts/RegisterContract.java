package com.thopv.projects.libraryforreader.welcome.presentation.contracts;


import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

/**
 * Created by jlaotsezu on 23/11/2017.
 */

public interface RegisterContract {
    interface View {
        void showMessage(String message);
        void startLoginView(User user);
        String getUserName();
        String getPassword();
        String getFullName();
        String getEmail();
        String getPhone();
        void showUserNameError(String s);
        void showPasswordError(String s);
        void showError(String existingError);
    }
    interface Presenter{
        void register();
    }
}
