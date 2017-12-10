package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.DisableBorrowCardUseCase;
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

public class DisableBorrowCardUseCaseTest {

    private Repository<Integer, BorrowCard> borrowCardRepository;
    private DisableBorrowCardUseCase useCase;

    @Before
    public void setUp() throws Exception {
        borrowCardRepository = mock(Repository.class);
        useCase = new DisableBorrowCardUseCase(borrowCardRepository);
    }
    @Test
    public void disableNonExistingBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(null);

        useCase.setRequestValues(new DisableBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), DisableBorrowCardUseCase.DON_T_HAVE_ANY_BORROW_CARD_MATCHING);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        useCase.run();
    }
    @Test
    public void disableExistingBorrowCard_ButAdreadyDiabled(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.deActive();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);

        useCase.setRequestValues(new DisableBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), DisableBorrowCardUseCase.BORROW_CARD_IS_ADREADY_DISABLED);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        useCase.run();
    }
    @Test
    public void disableExistingBorrowCard_ButHaveSomeInternalDbError(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(borrowCardRepository.update(any())).thenReturn(false);

        useCase.setRequestValues(new DisableBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), DisableBorrowCardUseCase.DISABLE_BORROW_CARD_FAILED_INTERNAL_DB_ERROR);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(borrowCardRepository).update(any());
        });

        useCase.run();
    }
    @Test
    public void happyDisableExistingBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(borrowCardRepository.update(any())).thenReturn(true);

        useCase.setRequestValues(new DisableBorrowCardUseCase.RequestValues(borrowCard.getBorrowCardId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(borrowCardRepository).update(any());
        });

        useCase.run();
    }
}