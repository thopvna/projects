package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;

import java.util.List;

public interface BorrowBooksContract {
    interface Controller extends BaseController {
        void showBookCopy(BookCopy bookCopy);
        void showLoan(Loan loan);
        void showCards(List<BorrowCard> cards);
        void clearViews();
    }
    interface Presenter extends BasePresenter {
        void borrowBooks(int librarianId, int borrowCardId, List<Integer> books);
        void fetchBorrowCards(String keyword);
        void fetchBookCopy(int bookCopyId);
    }
}
