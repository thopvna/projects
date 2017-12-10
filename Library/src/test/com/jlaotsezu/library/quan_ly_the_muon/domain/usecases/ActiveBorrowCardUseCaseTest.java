package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.ActiveBorrowCardUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActiveBorrowCardUseCaseTest {

    private ActiveBorrowCardUseCase useCase;
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Before
    public void setUp() throws Exception {
        borrowCardRepository = mock(Repository.class);
        useCase = new ActiveBorrowCardUseCase(borrowCardRepository);
    }
    @Test
    public void activeNonExistingBorrowCard(){
        int borrowCardId = TestUtils.genAbsInt();
        when(borrowCardRepository.fetchById(borrowCardId)).thenReturn(null);
        useCase.setRequestValues(new ActiveBorrowCardUseCase.RequestValues(borrowCardId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ActiveBorrowCardUseCase.BORROW_CARD_IS_NOT_EXISTING);
            verify(borrowCardRepository).fetchById(borrowCardId);
        });
        useCase.run();
    }
    @Test
    public void activeExistingBorrowCard_ButAdreadyActive(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.active();
        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        useCase.setRequestValues(new ActiveBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ActiveBorrowCardUseCase.BORROW_CARD_ALREADY_ACTIVE);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });
        useCase.run();
    }
    @Test
    public void activeExistingBorrowCard_ButHaveInternalDbError(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.deActive();
        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(borrowCardRepository.update(borrowCard)).thenReturn(false);
        useCase.setRequestValues(new ActiveBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ActiveBorrowCardUseCase.DATABASE_INTERNAL_ERROR);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(borrowCardRepository).update(borrowCard);
        });
        useCase.run();
    }
    @Test
    public void happyActiveExistingBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.deActive();
        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(borrowCardRepository.update(borrowCard)).thenReturn(true);
        useCase.setRequestValues(new ActiveBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(borrowCardRepository).update(borrowCard);
        });
        useCase.run();
    }
}