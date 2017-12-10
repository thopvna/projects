package com.thopv.projects.libraryforreader.welcome.presentation.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.dagger.usecase.UseCaseProvider;
import com.thopv.projects.libraryforreader.data.source.schemas.SUser;
import com.thopv.projects.libraryforreader.home.presentation.views.HomeView;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.support.utils.ToastUtils;
import com.thopv.projects.libraryforreader.support.utils.ViewUtils;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;
import com.thopv.projects.libraryforreader.welcome.presentation.contracts.LoginContract;
import com.thopv.projects.libraryforreader.welcome.presentation.presenters.LoginPresenter;

import junit.framework.Assert;

public class LoginView extends AppCompatActivity implements LoginContract.View {
    private static final int REQUEST_CODE = 21295;
    TextView userNameView, passwordView;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_controller);

        userNameView = findViewById(R.id.userNameView);
        passwordView = findViewById(R.id.passwordView);

        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        loginPresenter = new LoginPresenter(UseCaseHandler.getInstance(), this, useCaseProvider.getLogin());

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            loginPresenter.login();
        });
        findViewById(R.id.registerView).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            startRegisterView();
        });
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.getInstance(this).showMessage(message);
    }

    @Override
    public void startHomeView(User user) {
        Intent intent = new Intent(this, HomeView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void showUserNameError(String message) {
        showMessage(message);
        userNameView.requestFocus();
    }

    @Override
    public void showPasswordError(String message) {
        showMessage(message);
        passwordView.requestFocus();
    }


    @Override
    public String getUserName() {
        return userNameView.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordView.getText().toString();
    }

    @Override
    public void startRegisterView() {
        Intent intent = new Intent(this, RegisterView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void showWrongCridentialError(String message) {
        showMessage(message);
        userNameView.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Assert.assertNotNull(data);
            Assert.assertNotNull(data.getExtras());
            User user = new Gson().fromJson(data.getExtras().getString("user"), User.class);
            userNameView.setText(user.getUserName());
            passwordView.setText(user.getPassword());
        }
    }
}
