package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;

import java.util.List;

public interface BorrowBooksRequestsContract {
    interface Controller extends BaseController{
        void showBorrowBooksRequests(List<BorrowBooksRequest> requests);
        void showLoan(Loan loan);
        void updateRequest(BorrowBooksRequest request);
    }
    interface Presenter extends BasePresenter{
        void fetchBorrowBooksRequests(String keyword);
        void confirmBorrowBooksRequest(int requestId, int librarianId, int borrowCard);
    }
}
