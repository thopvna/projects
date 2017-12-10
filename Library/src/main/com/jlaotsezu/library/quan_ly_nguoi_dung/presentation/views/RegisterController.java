package main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.views;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.resources.URLProvider;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts.RegisterContract;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements RegisterContract.Controller, Initializable{
    public TextField userNameView;
    public TextField passwordView;
    public TextField fullNameView;
    public TextField emailView;
    public TextField phoneView;
    public Button registerButton;
    public RadioButton maleView;
    public RadioButton femaleView;
    public ToggleGroup genderGroup;
    public ImageView backButton;

    @Autowired
    @Qualifier("registerPresenter")
    RegisterContract.Presenter presenter;
    private App app;
    private Intent intent;

    private Parent viewContainer;

    @Override
    public void finish(User user) {
        Intent intent = new Intent(URLProvider.getLoginURL());
        intent.putExtra("user", new Gson().toJson(user));
        app.startController(intent);
    }

    @Override
    public void finish() {
        Intent intent = new Intent(URLProvider.getLoginURL());
        app.startController(intent);
    }

    @Override
    public String getUserName() {
        return userNameView.getText();
    }

    @Override
    public String getPassword() {
        return passwordView.getText();
    }

    @Override
    public String getFullName() {
        return fullNameView.getText();
    }

    @Override
    public int getGender() {
        return maleView.isSelected() ? 1 : 0;
    }

    @Override
    public String getEmail() {
        return emailView.getText();
    }

    @Override
    public String getPhone() {
        return phoneView.getText();
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Register result", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Register error", error);
    }

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }

    public void onRegisterButtonClicked(ActionEvent actionEvent) {
        presenter.register();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onBackButtonClicked(MouseEvent mouseEvent) {
        finish();
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            presenter.register();
        }
    }
}
