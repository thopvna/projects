package main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases.LoginUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts.LoginContract;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginPresenter implements LoginContract.Presenter {
    public static final String PAYLOAD_EMPTY = "Đăng nhập thất bại, Lỗi UseCase.";
    public static final String LOGIN_SUCCESS = "Đăng nhập thành công.";
    public static final String NOT_A_LIBRARIAN = "Người dùng hiện tại không phải Thủ thư. Hãy đăng nhập bằng tài khoản khác.";
    @Autowired
    LoginContract.Controller loginController;
    @Autowired
    LoginUseCase loginUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Override
    public void login() {
        String userName = loginController.getUserName();
        String password = loginController.getPassword();

        useCaseHandler.execute(loginUseCase, new LoginUseCase.RequestValues(userName, password), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    loginController.showError(PAYLOAD_EMPTY);
                }
                else {
                    User librarian = response.getPayload().getUser();
                    if (librarian == null || !librarian.isLibrarian()) {
                        loginController.showError(NOT_A_LIBRARIAN);
                    } else {
                        loginController.showMessage(LOGIN_SUCCESS);
                        loginController.startHomeScreen(response.getPayload().getUser());
                    }
                }
            }
            else{
                loginController.showError("Đăng nhập thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
