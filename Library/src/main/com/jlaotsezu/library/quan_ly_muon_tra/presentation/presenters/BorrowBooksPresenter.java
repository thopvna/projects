package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.FetchBookCopyUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.NewMannerBorrowBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.BorrowBooksContract;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.FetchBorrowCardsUseCase;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BorrowBooksPresenter implements BorrowBooksContract.Presenter{
    public static final String FETCH_BOOKCOPY_ERROR_PAYLOAD_EMPTY = "Lỗi Fetch BookCopy Usecase. Payload rỗng.";
    public static final String FETCH_BOOKCOPY_ERROR_BOOK_COPY_NULL = "Lỗi Fetch BookCopy Usecase. BookCopy rỗng.";
    public static final String BORROW_BOOKS_USECASE_ERROR_EMPTY_PAYLOAD = "Lỗi Borrow Books Usecase. Payload rỗng.";
    public static final String BORROW_BOOKS_USECASE_ERROR_LOAN_NULL = "Lỗi Borrow Books Usecase. Loan rỗng.";
    public static final String LOAD_BORROW_CARDS_USECASE_ERROR_PAYLOAD_EMPTY = "Lỗi Load BorrowCard Usecase. Payload rỗng.";
    public static final String LOAD_BORROW_CARDS_DONT_HAVE_ANY_BORROWCARDS = "Lỗi Load BorrowCard Usecase. Không có BorrowCards nào khớp.";
    @Autowired
    NewMannerBorrowBooksUseCase newMannerBorrowBooksUseCase;
    @Autowired
    FetchBookCopyUseCase fetchBookCopyUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    BorrowBooksContract.Controller borrowBooksController;
    @Autowired
    FetchBorrowCardsUseCase fetchBorrowCardsUseCase;
    @Override
    public void borrowBooks(int librarianId, int borrowCardId, List<Integer> books) {
        useCaseHandler.execute(newMannerBorrowBooksUseCase, new NewMannerBorrowBooksUseCase.RequestValues(librarianId, borrowCardId, books), response ->{
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    borrowBooksController.showError(BORROW_BOOKS_USECASE_ERROR_EMPTY_PAYLOAD);
                }
                else{
                    Loan loan = response.getPayload().getLoan();
                    if(loan == null){
                        borrowBooksController.showError(BORROW_BOOKS_USECASE_ERROR_LOAN_NULL);
                    }
                    else{
                        borrowBooksController.showLoan(loan);
                        borrowBooksController.clearViews();
                    }
                }
            }
            else{
                borrowBooksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void fetchBorrowCards(String keyword) {
        useCaseHandler.execute(fetchBorrowCardsUseCase, new FetchBorrowCardsUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    borrowBooksController.showError(LOAD_BORROW_CARDS_USECASE_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<BorrowCard> borrowCards = response.getPayload().getBorrowCards();
                    if(borrowCards == null || borrowCards.size() == 0){
                        borrowBooksController.showError(LOAD_BORROW_CARDS_DONT_HAVE_ANY_BORROWCARDS);
                    }
                    else{
                        borrowBooksController.showCards(borrowCards);
                    }
                }
            }
            else{
                borrowBooksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void fetchBookCopy(int bookCopyId) {
        useCaseHandler.execute(fetchBookCopyUseCase, new FetchBookCopyUseCase.RequestValues(bookCopyId), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    borrowBooksController.showError(FETCH_BOOKCOPY_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    BookCopy bookCopy = response.getPayload().getBookCopy();
                    if(bookCopy == null){
                        borrowBooksController.showError(FETCH_BOOKCOPY_ERROR_BOOK_COPY_NULL);
                    }
                    else{
                        borrowBooksController.showBookCopy(bookCopy);
                    }
                }
            }
            else{
                borrowBooksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }


    @Override
    public void start() {

    }
}
