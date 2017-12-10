package test.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.FindBorrowerUseCase;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.IssueBorrowCardUseCase;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts.IssueBorrowCardContract;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters.IssueBorrowCardPresenter;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.com.jlaotsezu.library.TestUtils;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class IssueBorrowCardPresenterTest {
    @Mock
    FindBorrowerUseCase findBorrowerUseCase;
    @Mock
    IssueBorrowCardUseCase issueBorrowCardUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    IssueBorrowCardContract.Controller issueBorrowCardController;
    @InjectMocks
    IssueBorrowCardPresenter issueBorrowCardPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void findBorrower_WithFailResponse(){
        String keyword = "any";
        String message = "any";
        doAnswer(invocation -> {
            FindBorrowerUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, FindBorrowerUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.findBorrower(keyword);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(contains(message));
    }
    @Test
    public void findBorrower_WithEmptyPayload(){
        String keyword = "any";
        doAnswer(invocation -> {
            FindBorrowerUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, FindBorrowerUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.findBorrower(keyword);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(IssueBorrowCardPresenter.FIND_BORROWER_USECASE_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void findBorrower_WithEmptyBorrower(){
        String keyword = "any";
        doAnswer(invocation -> {
            FindBorrowerUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, FindBorrowerUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FindBorrowerUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.findBorrower(keyword);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(IssueBorrowCardPresenter.FIND_BORROWER_USECASE_ERROR_DONT_HAVE_ANY_BORROWER);
    }
    @Test
    public void findBorrower_WithResponseIsNotABorrower(){
        User nonBorrower = TestUtils.genLibrarian();
        String keyword = "any";
        doAnswer(invocation -> {
            FindBorrowerUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, FindBorrowerUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FindBorrowerUseCase.ResponseValues(Collections.singletonList(nonBorrower))));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.findBorrower(keyword);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(IssueBorrowCardPresenter.FIND_BORROWER_USECASE_ERROR_USER_ISNOT_BORROWER);
    }
    @Test
    public void happyFindBorrower(){
        User borrower = TestUtils.genBorrower();
        String keyword = "any";
        doAnswer(invocation -> {
            FindBorrowerUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, FindBorrowerUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FindBorrowerUseCase.ResponseValues(Collections.singletonList(borrower))));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.findBorrower(keyword);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController, never()).showError(IssueBorrowCardPresenter.FIND_BORROWER_USECASE_ERROR_USER_ISNOT_BORROWER);
        verify(issueBorrowCardController).showBorrowers(Collections.singletonList(borrower));
    }

    @Test
    public void issueBorrowCard_WithFailResponse(){
        int borrowerId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            IssueBorrowCardUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, IssueBorrowCardUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.issueBorrowCard(borrowerId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(contains(message));
    }
    @Test
    public void issueBorrowCard_WithEmptyPayload(){
        int borrowerId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            IssueBorrowCardUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, IssueBorrowCardUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.issueBorrowCard(borrowerId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(IssueBorrowCardPresenter.ISSUE_BORROW_CARD_USECASE_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void issueBorrowCard_WithBorrowCardEmpty(){
        int borrowerId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            IssueBorrowCardUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, IssueBorrowCardUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new IssueBorrowCardUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.issueBorrowCard(borrowerId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController).showError(IssueBorrowCardPresenter.ISSUE_BORROW_CARD_USECASE_ERROR_BORROW_CARD_IS_NULL);
    }
    @Test
    public void happyIssueBorrowCard(){
        int borrowerId = TestUtils.genAbsInt();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        doAnswer(invocation -> {
            IssueBorrowCardUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, IssueBorrowCardUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new IssueBorrowCardUseCase.ResponseValues(borrowCard)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        issueBorrowCardPresenter.issueBorrowCard(borrowerId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(issueBorrowCardController, never()).showError(IssueBorrowCardPresenter.ISSUE_BORROW_CARD_USECASE_ERROR_BORROW_CARD_IS_NULL);
        verify(issueBorrowCardController).showBorrowCard(borrowCard);
    }
}