package test.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.FetchBookCopyUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.NewMannerBorrowBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.BorrowBooksContract;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.BorrowBooksPresenter;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.FetchBorrowCardsUseCase;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BorrowBooksPresenterTest {
    @Mock
    NewMannerBorrowBooksUseCase newMannerBorrowBooksUseCase;
    @Mock
    FetchBookCopyUseCase fetchBookCopyUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    BorrowBooksContract.Controller borrowBooksController;
    @Mock
    FetchBorrowCardsUseCase fetchBorrowCardsUseCase;
    @InjectMocks
    BorrowBooksPresenter borrowBooksPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void fetchBookCopy_WithFailResponse(){
        int bookCopyId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.fetchBookCopy(bookCopyId);

        verify(borrowBooksController).showError(contains(message));
        verify(useCaseHandler).execute(any(), any(), any());
    }
    @Test
    public void fetchBookCopy_WithEmptyPayload(){
        int bookCopyId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.fetchBookCopy(bookCopyId);

        verify(borrowBooksController).showError(BorrowBooksPresenter.FETCH_BOOKCOPY_ERROR_PAYLOAD_EMPTY);
        verify(useCaseHandler).execute(any(), any(), any());
    }
    @Test
    public void fetchBookCopy_WithNullBookCopy(){
        int bookCopyId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBookCopyUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.fetchBookCopy(bookCopyId);

        verify(borrowBooksController).showError(BorrowBooksPresenter.FETCH_BOOKCOPY_ERROR_BOOK_COPY_NULL);
        verify(useCaseHandler).execute(any(), any(), any());
    }
    @Test
    public void happyFetchBookCopy(){
        BookCopy bookCopy = TestUtils.genBorrowableBookCopy();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBookCopyUseCase.ResponseValues(bookCopy)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.fetchBookCopy(bookCopy.getBookCopyId());

        verify(borrowBooksController).showBookCopy(bookCopy);
        verify(useCaseHandler).execute(any(), any(), any());
    }

    @Test
    public void borrowBooks_WithFailResponse(){
        String message = "any";
        int librarianId = TestUtils.genAbsInt();
        int borrowerId = TestUtils.genAbsInt();
        List<Integer> bookCopies = TestUtils.genInts(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.borrowBooks(librarianId, borrowerId, bookCopies);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController).showError(contains(message));
    }
    @Test
    public void borrowBooks_WithEMptyPayload(){
        int librarianId = TestUtils.genAbsInt();
        int borrowerId = TestUtils.genAbsInt();
        List<Integer> bookCopies = TestUtils.genInts(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.borrowBooks(librarianId, borrowerId, bookCopies);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController).showError(BorrowBooksPresenter.BORROW_BOOKS_USECASE_ERROR_EMPTY_PAYLOAD);
    }
    @Test
    public void borrowBooks_WithNullLoan(){
        Loan loan = TestUtils.genLoan();
        List<Integer> bookCopies = TestUtils.genInts(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new NewMannerBorrowBooksUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.borrowBooks(loan.getLibrarian().getUserId(), loan.getBorrower().getUserId(), bookCopies);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController).showError(BorrowBooksPresenter.BORROW_BOOKS_USECASE_ERROR_LOAN_NULL);
    }
    @Test
    public void happyBorrowBooks(){
        Loan loan = TestUtils.genLoan();
        List<Integer> bookCopies = TestUtils.genInts(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new NewMannerBorrowBooksUseCase.ResponseValues(loan)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.borrowBooks(loan.getLibrarian().getUserId(), loan.getBorrower().getUserId(), bookCopies);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController, never()).showError(any());
        verify(borrowBooksController).showLoan(loan);
        verify(borrowBooksController).clearViews();
    }

    @Test
    public void fetchBorrowCards_WithFailResponse(){
        String message = "any";
        String keyword = "keyword";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksPresenter.fetchBorrowCards(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController).showError(contains(message));
    }
    @Test
    public void fetchBorrowCards_WithEMptyPayload(){
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        String keyword = "keyword";
        borrowBooksPresenter.fetchBorrowCards(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController).showError(BorrowBooksPresenter.LOAD_BORROW_CARDS_USECASE_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void fetchBorrowCards_WithNullCards(){
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBorrowCardsUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        String keyword = "keyword";
        borrowBooksPresenter.fetchBorrowCards(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController).showError(BorrowBooksPresenter.LOAD_BORROW_CARDS_DONT_HAVE_ANY_BORROWCARDS);
    }
    @Test
    public void happyFetchBorrowCards(){
        List<BorrowCard> cards = TestUtils.genBorrowCards(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBorrowCardsUseCase.ResponseValues(cards)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        String keyword = "keyword";
        borrowBooksPresenter.fetchBorrowCards(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksController, never()).showError(any());
        verify(borrowBooksController).showCards(cards);
    }
}