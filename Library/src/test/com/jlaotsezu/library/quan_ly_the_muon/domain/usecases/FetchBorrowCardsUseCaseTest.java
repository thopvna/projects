package test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.FetchBorrowCardsUseCase;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FetchBorrowCardsUseCaseTest {

    private FetchBorrowCardsUseCase useCase;
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Before
    public void setUp() throws Exception {
        borrowCardRepository = mock(Repository.class);
        useCase = new FetchBorrowCardsUseCase( borrowCardRepository);
    }
    @Test
    public void fetchBorrowCards_WithEmptyKeyword(){
        String keyword = "";
        useCase.setRequestValues(new FetchBorrowCardsUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FetchBorrowCardsUseCase.INPUT_INVALID_KEYWORD_EMPTY);
        });
        useCase.run();
    }
    @Test
    public void fetchBorrowCards_ButDontHaveAnyCards(){
        User user = TestUtils.genUser();
        String keyword = "any";
        when(borrowCardRepository.find(any())).thenReturn(null);

        useCase.setRequestValues(new FetchBorrowCardsUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FetchBorrowCardsUseCase.USER_DON_T_HAVE_ANY_BORROW_CARDS);
            verify(borrowCardRepository, atLeastOnce()).find(any());
        });

        useCase.run();
    }
    @Test
    public void happyFetchBorrowCards(){
        User user = TestUtils.genUser();
        String keyword = "any";

        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);

        when(borrowCardRepository.find(any())).thenReturn(borrowCards);

        useCase.setRequestValues(new FetchBorrowCardsUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            assertNotNull(response.getPayload());
            Assertions.assertThat(response.getPayload().getBorrowCards()).isNotNull().isNotEmpty().isEqualTo(borrowCards);
            verify(borrowCardRepository, atLeastOnce()).find(any());
        });

        useCase.run();
    }
}