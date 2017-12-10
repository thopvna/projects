package main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;

/**
 * Định nghĩa contract giữa phần Controller và Presenter của use case đăng ký
 */

public interface RegisterContract {
    interface Controller extends BaseController {
        void finish(User user);
        void finish();
        String getUserName();
        String getPassword();
        String getFullName();
        int getGender();
        String getEmail();
        String getPhone();
    }
    interface Presenter extends BasePresenter{
        void register();
    }
}
