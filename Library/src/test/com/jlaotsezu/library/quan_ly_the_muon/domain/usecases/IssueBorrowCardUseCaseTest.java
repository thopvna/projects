package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.IssueBorrowCardUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IssueBorrowCardUseCaseTest {

    private Repository<Integer, User> userRepository;
    private Repository<Integer, BorrowCard> borrowCardRepository;
    private IssueBorrowCardUseCase useCase;

    @Before
    public void setUp() throws Exception {
        userRepository = mock(Repository.class);
        borrowCardRepository = mock(Repository.class);
        useCase = new IssueBorrowCardUseCase(userRepository, borrowCardRepository);
    }
    @Test
    public void issueBorrowCardForNonExistingUser(){
        User user = TestUtils.genUser();
        useCase.setRequestValues(new IssueBorrowCardUseCase.RequestValues(user.getUserId()));
        when(userRepository.fetchById(user.getUserId())).thenReturn(null);

        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), IssueBorrowCardUseCase.DON_T_HAVE_ANY_USER_MATCHING);
            verify(userRepository).fetchById(user.getUserId());
        });

        useCase.run();
    }
    @Test
    public void issueBorrowCardForExistingUser_ButAdreadyHaveBorrowCard(){
        User user = TestUtils.genUser();
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);

        when(userRepository.fetchById(user.getUserId())).thenReturn(user);
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);

        useCase.setRequestValues(new IssueBorrowCardUseCase.RequestValues(user.getUserId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), IssueBorrowCardUseCase.USER_ADREADY_HAVE_BORROW_CARDS);
            verify(userRepository).fetchById(user.getUserId());
            verify(borrowCardRepository).find(any());
        });

        useCase.run();
    }
    @Test
    public void issueBorrowCardForExistingUser_DontHaveAnyBorrowCard_Happy(){
        User user = TestUtils.genUser();

        when(userRepository.fetchById(user.getUserId())).thenReturn(user);
        when(borrowCardRepository.find(any())).thenReturn(null);
        when(borrowCardRepository.save(any())).thenReturn(true);

        useCase.setRequestValues(new IssueBorrowCardUseCase.RequestValues(user.getUserId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(userRepository).fetchById(user.getUserId());
            verify(borrowCardRepository).find(any());
            verify(borrowCardRepository).save(any());
        });

        useCase.run();
    }
}