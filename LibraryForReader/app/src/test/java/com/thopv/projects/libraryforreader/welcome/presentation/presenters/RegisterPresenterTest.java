package com.thopv.projects.libraryforreader.welcome.presentation.presenters;

import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Register;
import com.thopv.projects.libraryforreader.welcome.presentation.contracts.RegisterContract;
import com.thopv.projects.libraryforreader.welcome.presentation.presenters.RegisterPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by thopv on 12/3/2017.
 */
public class RegisterPresenterTest {
     RegisterContract.View registerView;
    RegisterPresenter registerPresenter;
     Register register;
     UseCaseHandler useCaseHandler;

    @Before
    public void setUp() throws Exception {
        registerView = mock(RegisterContract.View.class);
        register = mock(Register.class);
        useCaseHandler = mock(UseCaseHandler.class);
        registerPresenter = new RegisterPresenter(useCaseHandler, register, registerView);
    }
    @Test
    public void registerWithUserNameEmpty(){
        when(registerView.getUserName()).thenReturn("");

        registerPresenter.register();

        verify(registerView).showUserNameError(anyString());
    }
    @Test
    public void registerWithPasswordEmpty(){
        when(registerView.getUserName()).thenReturn("NonEmpty");
        when(registerView.getPassword()).thenReturn("");

        registerPresenter.register();

        verify(registerView, never()).showUserNameError(anyString());
        verify(registerView).showPasswordError(anyString());
    }
    @Test
    public void registerWithUserNameAndPasswordNonEmptyButExisting(){
        User user = new User("thopvna", "1", "Pham Van Tho", "email","0965784238", false);
        when(registerView.getUserName()).thenReturn(user.getUserName());
        when(registerView.getPassword()).thenReturn(user.getPassword());
        when(registerView.getFullName()).thenReturn(user.getFullName());
        when(registerView.getEmail()).thenReturn(user.getEmail());
        when(registerView.getPhone()).thenReturn(user.getPhone());

        String errorMessage = "user is existing.";

        doAnswer(invocation -> {
            UseCaseCallback<Register.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(errorMessage));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());


        registerPresenter.register();

        verify(registerView, never()).showUserNameError(anyString());
        verify(registerView, never()).showPasswordError(anyString());
        verify(registerView).showError(contains(errorMessage));
    }
    @Test
    public void registerWithUserNameAndPasswordNonEmptyAndNonExisting() throws Exception {
        User user = new User("thopvna", "1", "Pham Van Tho", "email","0965784238", false);
        when(registerView.getUserName()).thenReturn(user.getUserName());
        when(registerView.getPassword()).thenReturn(user.getPassword());
        when(registerView.getFullName()).thenReturn(user.getFullName());
        when(registerView.getEmail()).thenReturn(user.getEmail());
        when(registerView.getPhone()).thenReturn(user.getPhone());

        doAnswer(invocation -> {
            UseCaseCallback<Register.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        registerPresenter.register();

        verify(registerView, never()).showUserNameError(anyString());
        verify(registerView, never()).showPasswordError(anyString());
        verify(registerView, never()).showError(anyString());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(registerView).startLoginView(userCaptor.capture());
        assertEquals(user.getUserName(), userCaptor.getValue().getUserName());
    }

}