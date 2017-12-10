package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.FindBorrowerUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FindBorrowerUseCaseTest {

    private FindBorrowerUseCase useCase;
    private Repository<Integer, User> userRepository;

    @Before
    public void setup(){
        userRepository = mock(Repository.class);
        useCase = new FindBorrowerUseCase(userRepository);
    }
    @Test
    public void findBorrowerWithUserNameEmpty(){
        useCase.setRequestValues(new FindBorrowerUseCase.RequestValues(null));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FindBorrowerUseCase.INPUT_INVALID_KEYWORD_IS_EMPTY);
        });
        useCase.run();
    }
    @Test
    public void findNonExistingBorrower(){
        String userName = "NonEmpty";
        when(userRepository.find(any())).thenReturn(null);
        useCase.setRequestValues(new FindBorrowerUseCase.RequestValues(userName));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FindBorrowerUseCase.DONT_HAVE_ANY_USERS_MATCHING);
            verify(userRepository).find(any());
        });
        useCase.run();
    }
    @Test
    public void findExistingUser_ButDontHaveAnyBorrowers(){
        User user = TestUtils.genLibrarian();
        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));
        useCase.setRequestValues(new FindBorrowerUseCase.RequestValues(user.getUserName()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FindBorrowerUseCase.DONT_HAVE_ANY_BORROWERS_MATCHING);
            verify(userRepository).find(any());
        });
        useCase.run();
    }
    @Test
    public void happyFindBorrower(){
        User user = TestUtils.genBorrower();
        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));
        useCase.setRequestValues(new FindBorrowerUseCase.RequestValues(user.getUserName()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(userRepository).find(any());
        });
        useCase.run();
    }
}