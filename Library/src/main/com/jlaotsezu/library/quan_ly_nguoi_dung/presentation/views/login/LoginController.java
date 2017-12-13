package main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.views;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.resources.URLProvider;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts.LoginContract;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class LoginController implements LoginContract.Controller {
    public Button loginButton;
    public TextField userNameField;
    public TextField passwordField;
    public Button registerButton;
    @Autowired
    @Qualifier("loginPresenter")
    LoginContract.Presenter presenter;
    private App app;
    private Intent intent;
    private Parent viewContainer;

    public void onLoginButtonClicked(ActionEvent actionEvent) {
        presenter.login();
    }

    @Override
    public void startRegisterScreen() {
        Intent intent = new Intent(URLProvider.getRegisterURL());
        app.startController(intent);
    }

    @Override
    public void startHomeScreen(User librarian) {
        Intent intent = new Intent(URLProvider.getHomeURL());
        intent.putExtra("librarian", new Gson().toJson(librarian));
        app.startController(intent);
    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }

    @Override
    public String getUserName() {
        return userNameField.getText();
    }

    @Override
    public String getPassword() {
        return passwordField.getText();
    }

    @Override
    public void showUser(User user) {
        if(user != null){
            userNameField.setText(user.getUserName());
            passwordField.setText(user.getPassword());
        }
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Kết quả đăng nhập", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi đăng nhập", error);
    }

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        if(intent != null && intent.getExtra("user") != null){
            User user = new Gson().fromJson(intent.getExtra("user"), User.class);
            userNameField.setText(user.getUserName());
            passwordField.setText(user.getPassword());
        }
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            presenter.login();
        }
    }

    public void onRegisterButtonClicked(ActionEvent actionEvent) {
        startRegisterScreen();
    }

}
