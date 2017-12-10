package com.thopv.projects.libraryforreader.welcome.presentation.presenters;

import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Login;
import com.thopv.projects.libraryforreader.welcome.presentation.contracts.LoginContract;
import com.thopv.projects.libraryforreader.welcome.presentation.presenters.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/2/2017.
 */
public class LoginPresenterTest {
    LoginContract.View loginView;
    LoginPresenter loginPresenter;
    UseCaseHandler useCaseHandler;
    Login login;
    @Before
    public void setUp() throws Exception {
        loginView = mock(LoginContract.View.class);
        useCaseHandler = mock(UseCaseHandler.class);
        login = mock(Login.class);
        loginPresenter = new LoginPresenter(useCaseHandler, loginView, login);
    }
    @Test
    public void loginWithEmptyUserName(){
        when(loginView.getUserName()).thenReturn("");
        loginPresenter.login();
        verify(loginView).showUserNameError(any());
    }
    @Test
    public void loginWithEmptyPassword(){
        when(loginView.getUserName()).thenReturn("NonEmpty");
        when(loginView.getPassword()).thenReturn("");
        loginPresenter.login();
        verify(loginView, never()).showUserNameError(any());
        verify(loginView).showPasswordError(any());
    }
    @Test
    public void loginWithWrongCridential(){
        when(loginView.getUserName()).thenReturn("NonEmpty");
        when(loginView.getPassword()).thenReturn("NonEmpty");

        doAnswer(invocation -> {
            UseCaseCallback<UseCase.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail("Username or password is wrong."));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        loginPresenter.login();

        verify(loginView, never()).showUserNameError(any());
        verify(loginView, never()).showPasswordError(any());
        verify(loginView).showWrongCridentialError(any());

    }
    @Test
    public void loginWithRightCridential(){
        User user = new User("thopvna", "1", "Pham Van Tho", "thopvna@gmail.com", "0965784238", false);

        when(loginView.getUserName()).thenReturn(user.getUserName());
        when(loginView.getPassword()).thenReturn(user.getPassword());

        doAnswer(invocation -> {
            UseCaseCallback<Login.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new Login.ResponseValues(user)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        loginPresenter.login();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(loginView, never()).showUserNameError(any());
        verify(loginView, never()).showPasswordError(any());
        verify(loginView, never()).showWrongCridentialError(any());

        verify(loginView).startHomeView(userCaptor.capture());
        assertEquals(user.getUserName(), userCaptor.getValue().getUserName());
        assertEquals(user.getPassword(), userCaptor.getValue().getPassword());
    }
}