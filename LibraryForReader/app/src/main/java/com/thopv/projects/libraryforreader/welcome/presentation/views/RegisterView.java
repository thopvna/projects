package com.thopv.projects.libraryforreader.welcome.presentation.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.dagger.usecase.UseCaseProvider;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.support.utils.ToastUtils;
import com.thopv.projects.libraryforreader.support.utils.ViewUtils;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;
import com.thopv.projects.libraryforreader.welcome.presentation.contracts.RegisterContract;
import com.thopv.projects.libraryforreader.welcome.presentation.presenters.RegisterPresenter;

public class RegisterView extends AppCompatActivity implements RegisterContract.View{
    private TextView userNameView, passwordView, fullNameView, emailView, phoneView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_controller);
        userNameView = findViewById(R.id.userNameView);
        passwordView = findViewById(R.id.passwordView);
        fullNameView = findViewById(R.id.fullNameView);
        emailView = findViewById(R.id.emailView);
        phoneView = findViewById(R.id.phoneView);

        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        RegisterPresenter registerPresenter = new RegisterPresenter(UseCaseHandler.getInstance(), useCaseProvider.getRegister(), this);

        findViewById(R.id.registerButton).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            registerPresenter.register();
        });
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.getInstance(this).showMessage(message);
    }

    @Override
    public void startLoginView(User user) {
        Intent data = new Intent(this, LoginView.class);
        data.putExtra("user", new Gson().toJson(user));
        setResult(RESULT_OK, data);
        finish();
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
    public String getFullName() {
        return fullNameView.getText().toString();
    }

    @Override
    public String getEmail() {
        return emailView.getText().toString();
    }

    @Override
    public String getPhone() {
        return phoneView.getText().toString();
    }

    @Override
    public void showUserNameError(String s) {
        userNameView.requestFocus();
        showMessage(s);
    }

    @Override
    public void showPasswordError(String s) {
        passwordView.requestFocus();
        showMessage(s);
    }

    @Override
    public void showError(String s) {
        userNameView.requestFocus();
        showMessage(s);
    }
}
