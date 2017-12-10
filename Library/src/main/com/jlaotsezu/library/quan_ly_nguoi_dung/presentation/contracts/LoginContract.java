package main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;

/**
 * Định nghĩa contract giữa phần Controller và Presenter của use case Login
 */

public interface LoginContract {
    interface Controller extends BaseController {
        void startRegisterScreen();
        void startHomeScreen(User librarian);
        String getUserName();
        String getPassword();

        void showUser(User user);
    }
    interface Presenter extends BasePresenter{
        void login();
    }
}
