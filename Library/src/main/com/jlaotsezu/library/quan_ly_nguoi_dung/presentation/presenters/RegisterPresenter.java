package main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.Role;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases.RegisterUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts.RegisterContract;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterPresenter implements RegisterContract.Presenter {
    @Autowired
    RegisterContract.Controller registerController;
    @Autowired
    RegisterUseCase registerUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;

    @Override
    public void register() {
        String userName = registerController.getUserName();
        String password = registerController.getPassword();
        String fullName = registerController.getFullName();
        int gender = registerController.getGender();
        String email = registerController.getEmail();
        String phone = registerController.getPhone();

        User user = new User.Builder()
                .setUserName(userName)
                .setPassword(password)
                .setFullName(fullName)
                .setGender(gender)
                .setEmail(email)
                .setPhone(phone)
                .setRole(Role.LIBRARICIAN)
                .build();

        useCaseHandler.execute(registerUseCase, new RegisterUseCase.RequestValues(user), response -> {
            if(response.isSuccess()){
                registerController.showMessage("Đăng ký thành công.");
                registerController.finish(user);
            }
            else{
                registerController.showError("Đăng ký thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
