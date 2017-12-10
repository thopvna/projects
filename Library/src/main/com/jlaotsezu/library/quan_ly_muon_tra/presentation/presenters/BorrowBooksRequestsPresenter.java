package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.ConfirmBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.FetchBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.LoadAllBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.BorrowBooksRequestsContract;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class BorrowBooksRequestsPresenter implements BorrowBooksRequestsContract.Presenter {

    public static final String FETCH_BORROW_BOOKS_REQUETS_ERROR_REQUESTS_EMPTY = "Lỗi Fetch BorrowBooks Requests Usecase. Requests rỗng.";
    public static final String FETCH_BORROW_BOOKS_REQUETS_ERROR_PAYLOAD_EMPTY = "Lỗi Fetch BorrowBooks Requests Usecase. Payload rỗng.";
    public static final String CONFIRM_BORROW_BOOKS_REQUEST_ERROR_REQUEST_NULL = "Lỗi Confirm Borrow Books Usecase. Request trong Response rỗng.";
    public static final String CONFIRM_BORROW_BOOKS_REQUEST_ERROR_LOAN_NULL = "Lỗi Confirm Borrow Books Usecase. Loan trong Response rỗng.";
    public static final String CONFIRM_BORROW_BOOKS_REQUEST_ERROR_PAYLOAD_EMPTY = "Lỗi Confirm Borrow Books Usecase. Payload rỗng.";
    @Autowired
    BorrowBooksRequestsContract.Controller borrowBooksRequestsController;
    @Autowired
    LoadAllBorrowBooksRequestsUseCase loadAllBorrowBooksRequestsUseCase;
    @Autowired
    FetchBorrowBooksRequestsUseCase fetchBorrowBooksRequestsUseCase;
    @Autowired
    ConfirmBorrowBooksRequestsUseCase confirmBorrowBooksRequestsUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;

    @Override
    public void fetchBorrowBooksRequests(String keyword) {
        useCaseHandler.execute(fetchBorrowBooksRequestsUseCase, new FetchBorrowBooksRequestsUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    borrowBooksRequestsController.showError(FETCH_BORROW_BOOKS_REQUETS_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<BorrowBooksRequest> requests = response.getPayload().getBorrowBooksRequests();
                    if(requests == null || requests.size() == 0){
                        borrowBooksRequestsController.showError(FETCH_BORROW_BOOKS_REQUETS_ERROR_REQUESTS_EMPTY);
                    }
                    else{
                        borrowBooksRequestsController.showBorrowBooksRequests(requests);
                    }
                }
            }
            else{
                borrowBooksRequestsController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void confirmBorrowBooksRequest(int requestId, int librarianId, int borrowCard) {
        useCaseHandler.execute(confirmBorrowBooksRequestsUseCase, new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCard, requestId, librarianId), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    borrowBooksRequestsController.showError(CONFIRM_BORROW_BOOKS_REQUEST_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    Loan loan = response.getPayload().getLoan();
                    BorrowBooksRequest request = response.getPayload().getRequest();
                    if(loan == null){
                        borrowBooksRequestsController.showError(CONFIRM_BORROW_BOOKS_REQUEST_ERROR_LOAN_NULL);
                    }
                    else if(request == null){
                        borrowBooksRequestsController.showError(CONFIRM_BORROW_BOOKS_REQUEST_ERROR_REQUEST_NULL);
                    }
                    else{
                        borrowBooksRequestsController.showLoan(loan);
                        borrowBooksRequestsController.updateRequest(request);
                    }
                }
            }
            else{
                borrowBooksRequestsController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
