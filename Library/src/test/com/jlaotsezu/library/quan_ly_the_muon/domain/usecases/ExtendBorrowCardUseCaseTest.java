package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.ExtendBorrowCardUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExtendBorrowCardUseCaseTest {

    private ExtendBorrowCardUseCase useCase;
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Before
    public void setUp() throws Exception {
        borrowCardRepository = mock(Repository.class);
        useCase = new ExtendBorrowCardUseCase(borrowCardRepository);
    }
    @Test
    public void extendNonExistingBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(null);

        useCase.setRequestValues(new ExtendBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ExtendBorrowCardUseCase.DON_T_HAVE_ANY_BORROW_CARD_MATCHING);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        useCase.run();
    }
    @Test
    public void extendDisableBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.disable();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);

        useCase.setRequestValues(new ExtendBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ExtendBorrowCardUseCase.BORROW_CARD_IS_DISABLED);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        useCase.run();
    }
    @Test
    public void extendNonExpiredBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.extend();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);

        useCase.setRequestValues(new ExtendBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ExtendBorrowCardUseCase.BORROW_CARD_IS_NOT_EXPIRED);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        useCase.run();
    }
    @Test
    public void extendExpiredBorrowCard_ButHaveSomeInternalDbError(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.setExpiredTime(0);
        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(borrowCardRepository.update(any())).thenReturn(false);

        useCase.setRequestValues(new ExtendBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ExtendBorrowCardUseCase.EXTEND_BORROW_CARD_FAILED_INTERNAL_DB_ERROR);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(borrowCardRepository).update(any());
        });

        useCase.run();
    }
    @Test
    public void happyExtendBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.setExpiredTime(0);
        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(borrowCardRepository.update(any())).thenReturn(true);

        useCase.setRequestValues(new ExtendBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(borrowCardRepository).update(any());
        });

        useCase.run();
    }
}