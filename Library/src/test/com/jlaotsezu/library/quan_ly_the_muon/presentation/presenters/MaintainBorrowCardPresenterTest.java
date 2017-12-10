package test.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.*;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts.MaintainBorrowCardContract;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters.MaintainBorrowCardPresenter;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class MaintainBorrowCardPresenterTest {
    @Mock
    FetchBorrowCardsUseCase fetchBorrowCardsUseCase;
    @Mock
    DisableBorrowCardUseCase disableBorrowCardUseCase;
    @Mock
    DisableBorrowCardsUseCase disableBorrowCardsUseCase;
    @Mock
    ExtendBorrowCardUseCase extendBorrowCardUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    MaintainBorrowCardContract.Controller maintainBorrowCardController;
    @Mock
    ActiveBorrowCardUseCase activeBorrowCardUseCase;
    @InjectMocks
    MaintainBorrowCardPresenter maintainBorrowCardPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void fetchBorrowCard_WithFailResponse(){
        int borrowerId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.loadBorrowCards(String.valueOf(borrowerId));
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(contains(message));
    }
    @Test
    public void fetchBorrowCard_WithEmptyPayload(){
        int borrowerId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.loadBorrowCards(String.valueOf(borrowerId));
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(MaintainBorrowCardPresenter.LOAD_BORROW_CARDS_USECASE_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void fetchBorrowCard_WIthEmptyBorrowCard(){
        int borrowerId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBorrowCardsUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.loadBorrowCards(String.valueOf(borrowerId));
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(MaintainBorrowCardPresenter.LOAD_BORROW_CARDS_DONT_HAVE_ANY_BORROWCARDS);
    }
    @Test
    public void happyFetchBorrowCard(){
        int borrowerId = TestUtils.genAbsInt();
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(4);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBorrowCardsUseCase.ResponseValues(borrowCards)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.loadBorrowCards(String.valueOf(borrowerId));
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showBorrowCards(borrowCards);
    }

    @Test
    public void disableBorrowCard_WIthFailResponse(){
        int borrowCardId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.disableBorrowCard(borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(contains(message));
    }
    @Test
    public void happyDisableBorrowCard(){
        int borrowCardId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.disableBorrowCard(borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showMessage(MaintainBorrowCardPresenter.DISABLE_BORROWCARD_SUCCESS);
    }

    @Test
    public void disableBorrowCards_WithFailResponse(){
        int borrowerId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.disableBorrowCards(borrowerId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(contains(message));
    }
    @Test
    public void happyDisableBorrowCards(){
        int borrowerId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.disableBorrowCards(borrowerId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showMessage(MaintainBorrowCardPresenter.DISABLE_BORROWCARDS_SUCCESS);
    }

    @Test
    public void extendBorrowCard_WithFailResponse(){
        String message = "any";
        int borrowCardId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.extendBorrowCard(borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(contains(message));
    }

    @Test
    public void happyExtendBorrowCard(){
        int borrowCardId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.extendBorrowCard(borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showMessage(MaintainBorrowCardPresenter.EXTEND_BORROWCARD_SUCCESS);
    }
    @Test
    public void activeBorrowCard_WithFailResponse(){
        String message = "any";
        int borrowCardId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.activeBorrowCard(borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showError(contains(message));
    }

    @Test
    public void happyActiveBorrowCard(){
        int borrowCardId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        maintainBorrowCardPresenter.activeBorrowCard(borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(maintainBorrowCardController).showMessage(MaintainBorrowCardPresenter.ACTIVE_BORROW_CARD_SUCCESS);
    }
}