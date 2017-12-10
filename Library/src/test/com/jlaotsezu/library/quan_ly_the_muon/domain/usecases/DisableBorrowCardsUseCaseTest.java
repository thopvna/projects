package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.DisableBorrowCardsUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DisableBorrowCardsUseCaseTest {

    private DisableBorrowCardsUseCase useCase;
    private Repository<Integer, User> userRepository;
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Before
    public void setUp() throws Exception {
        borrowCardRepository = mock(Repository.class);
        userRepository = mock(Repository.class);
        useCase = new DisableBorrowCardsUseCase(borrowCardRepository, userRepository);
    }
    @Test
    public void disableBorrowCardsOfNonExistingUser(){
        User user = TestUtils.genUser();
        useCase.setRequestValues(new DisableBorrowCardsUseCase.RequestValues(user.getUserId()));

        when(userRepository.fetchById(user.getUserId())).thenReturn(null);

        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), DisableBorrowCardsUseCase.DON_T_HAVE_ANY_USER_MATCHING);
            verify(userRepository).fetchById(user.getUserId());
        });

        useCase.run();
    }
    @Test
    public void disableBorrowCardOfExistingUser_ButDontHaveAnyCard(){
        User user = TestUtils.genUser();

        useCase.setRequestValues(new DisableBorrowCardsUseCase.RequestValues(user.getUserId()));

        when(userRepository.fetchById(user.getUserId())).thenReturn(user);
        when(borrowCardRepository.find(any())).thenReturn(null);

        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), DisableBorrowCardsUseCase.USER_DON_T_HAVE_ANY_BORROW_CARDS);
            verify(userRepository).fetchById(user.getUserId());
            verify(borrowCardRepository).find(any());
        });

        useCase.run();
    }
    @Test
    public void disableBorrowCardOfExistingUser_HaveCards_ButHaveSomeDbInternalError(){
        User user = TestUtils.genUser();
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);

        useCase.setRequestValues(new DisableBorrowCardsUseCase.RequestValues(user.getUserId()));

        when(userRepository.fetchById(user.getUserId())).thenReturn(user);
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);
        when(borrowCardRepository.updateAll(any())).thenReturn(false);

        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), DisableBorrowCardsUseCase.DISABLE_BORROW_CARDS_FAILED_INTERNAL_DB_ERROR);
            verify(userRepository).fetchById(user.getUserId());
            verify(borrowCardRepository).find(any());
            verify(borrowCardRepository).updateAll(any());
        });

        useCase.run();
    }
    @Test
    public void happyDisableBorrowCard(){
        User user = TestUtils.genUser();
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);

        useCase.setRequestValues(new DisableBorrowCardsUseCase.RequestValues(user.getUserId()));

        when(userRepository.fetchById(user.getUserId())).thenReturn(user);
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);
        when(borrowCardRepository.updateAll(any())).thenReturn(true);

        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(userRepository).fetchById(user.getUserId());
            verify(borrowCardRepository).find(any());
            verify(borrowCardRepository).updateAll(any());
        });

        useCase.run();
    }
}