package main.com.jlaotsezu.library.support.fx;

import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;

public interface Navigator {
    void showRegisterScreen();
    void showLoginScreen();
    void showLoginScreen(User user);
    void showHomeScreen(User librarian);
}
