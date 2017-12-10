package test.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases.LoginUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.contracts.LoginContract;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.presenters.LoginPresenter;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.com.jlaotsezu.library.TestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

public class LoginPresenterTest {
    @Mock
    LoginContract.Controller loginController;
    @Mock
    LoginUseCase loginUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @InjectMocks
    LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void loginWithFailResponse(){
        when(loginController.getUserName()).thenReturn("any");
        when(loginController.getPassword()).thenReturn("any");
        String message = "any";
        doAnswer(invocation -> {
            LoginUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoginUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());;
        loginPresenter.login();
        verify(useCaseHandler).execute(any(),any(),any());
        verify(loginController).showError(contains(message));
    }
    @Test
    public void loginWithSuccessResponse_ButPayloadNull(){
        when(loginController.getUserName()).thenReturn("NonEmpty");
        when(loginController.getPassword()).thenReturn("NonEmpty");
        doAnswer(invocation -> {
            LoginUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoginUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());;
        loginPresenter.login();
        verify(useCaseHandler).execute(any(),any(),any());
        verify(loginController).showError(LoginPresenter.PAYLOAD_EMPTY);
    }
    @Test
    public void loginWithSuccessResponse_ButUserIsNotALibrarian(){
        User user = TestUtils.genBorrower();
        doAnswer(invocation -> {
            LoginUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoginUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoginUseCase.ResponseValues(user)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());;
        loginPresenter.login();
        verify(useCaseHandler).execute(any(),any(),any());
        verify(loginController).showError(LoginPresenter.NOT_A_LIBRARIAN);
    }
    @Test
    public void happyLogin(){
        User user = TestUtils.genLibrarian();
        doAnswer(invocation -> {
            LoginUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoginUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoginUseCase.ResponseValues(user)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());;
        loginPresenter.login();
        verify(useCaseHandler).execute(any(),any(),any());
        verify(loginController, never()).showError(LoginPresenter.NOT_A_LIBRARIAN);
        verify(loginController).showMessage(LoginPresenter.LOGIN_SUCCESS);
        verify(loginController).startHomeScreen(user);
    }
}