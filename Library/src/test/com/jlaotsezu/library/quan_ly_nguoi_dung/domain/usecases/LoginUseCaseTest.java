package test.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases.LoginUseCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginUseCaseTest {

    private LoginUseCase loginUseCase;
    private Repository<Integer, User> userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = mock(Repository.class);
        loginUseCase = new LoginUseCase(userRepository);
    }
    @Test
    public void loginWithUserNameEmpty(){
        String userName = "";
        String password = "whatever";

        loginUseCase.setRequestValues(new LoginUseCase.RequestValues(userName, password));
        loginUseCase.setUseCaseCallback(response -> {
            Assert.assertFalse(response.isSuccess());
            Assert.assertEquals(response.getMessage(), LoginUseCase.USERNAME_IS_UNAVAILABLE);
        });
        loginUseCase.run();
    }
    @Test
    public void loginWithPasswordEmpty(){
        String userName = "NonEmpty";
        String password = "";

        loginUseCase.setRequestValues(new LoginUseCase.RequestValues(userName, password));
        loginUseCase.setUseCaseCallback(response -> {
            Assert.assertFalse(response.isSuccess());
            Assert.assertEquals(response.getMessage(), LoginUseCase.PASSWORD_IS_UNAVAILABLE);
        });
        loginUseCase.run();
    }
    @Test
    public void loginWithNonExistingUser(){
        String userName = "NonEmpty";
        String password = "NonEmpty";

        when(userRepository.find(any())).thenReturn(null);

        loginUseCase.setRequestValues(new LoginUseCase.RequestValues(userName, password));
        loginUseCase.setUseCaseCallback(response -> {
            Assert.assertFalse(response.isSuccess());
            Assert.assertEquals(response.getMessage(), LoginUseCase.USER_IS_NOT_EXISTING);
        });

        loginUseCase.run();
    }
    @Test
    public void loginWithExistingUser_ButPasswordWrong(){
        String userName = "NonEmpty";
        String password = "NonEmpty";

        User user = new User(userName, "otherNonEmpty", "" , 0, "", "", null);

        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));

        loginUseCase.setRequestValues(new LoginUseCase.RequestValues(userName, password));
        loginUseCase.setUseCaseCallback(response -> {
            Assert.assertFalse(response.isSuccess());
            Assert.assertEquals(response.getMessage(), LoginUseCase.PASSWORD_IS_WRONG);
        });

        loginUseCase.run();
    }
    @Test
    public void loginWithExistingUser_Happy(){
        String userName = "NonEmpty";
        String password = "NonEmpty";

        User user = new User(userName, password, "" , 0, "", "", null);

        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));

        loginUseCase.setRequestValues(new LoginUseCase.RequestValues(userName, password));
        loginUseCase.setUseCaseCallback(response -> {
            Assert.assertTrue(response.isSuccess());
            Assert.assertNotNull(response.getPayload());
            Assert.assertNotNull(response.getPayload().getUser());
            User payload = response.getPayload().getUser();
            Assert.assertEquals(payload.getUserName(), userName);
            Assert.assertEquals(payload.getPassword(), password);
        });

        loginUseCase.run();
    }
}