package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBorrowCardByUserId;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ConfirmBorrowBooksRequestsUseCase extends UseCase<ConfirmBorrowBooksRequestsUseCase.RequestValues, ConfirmBorrowBooksRequestsUseCase.ResponseValues> {
    public static final String BORROW_BOOKS_REQUEST_NON_EXISTING = "Yêu cầu mượn sách không tồn tại.";
    public static final String BORROW_BOOKS_REQUEST_ALREADY_CONFIRMED = "Yêu cầu mượn sách đã được confirm từ trước.";
    public static final String CONFIRM_BORROW_BOOKS_REQUEST_FAIL_INTERNAL_DB_ERROR = "Lỗi cơ sở dữ liệu";
    public static final String BORROW_BOOKS_REQUEST_IS_EXPIRED = "Yêu cầu mượn sách đã quá hạn.";
    public static final String BORROW_BOOKS_REQUEST_IS_NOT_ACCEPT = "Yêu cầu mượn sách vẫn chưa được chấp nhận.";
    public static final String BORROWCARD_IS_NOT_OF_BORROWER = "Thẻ mượn không phải thẻ của người gửi yêu cầu.";
    private Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository;
    private Repository<Integer, Loan> loanRepository;
    private Repository<Integer, BorrowCard> borrowCardRepository;
    private NewMannerBorrowBooksUseCase newMannerBorrowBooksUseCase;
    @Autowired
    public ConfirmBorrowBooksRequestsUseCase(Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository, Repository<Integer, Loan> loanRepository, Repository<Integer, BorrowCard> borrowCardRepository, NewMannerBorrowBooksUseCase newMannerBorrowBooksUseCase) {
        this.borrowBooksRequestRepository = borrowBooksRequestRepository;
        this.loanRepository = loanRepository;
        this.borrowCardRepository = borrowCardRepository;
        this.newMannerBorrowBooksUseCase = newMannerBorrowBooksUseCase;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int requestId = requestValues.getRequestId();
        int librarianId = requestValues.getLibrarianId();
        int borrowCardId = requestValues.getBorrowCardId();
        BorrowBooksRequest request = borrowBooksRequestRepository.fetchById(requestId);
        if(request == null){
            callback.onCompleted(ComplexResponse.fail(BORROW_BOOKS_REQUEST_NON_EXISTING));
        }
        else if(request.isConfirm()){
            callback.onCompleted(ComplexResponse.fail(BORROW_BOOKS_REQUEST_ALREADY_CONFIRMED));
        }
        else if(request.isExpired()){
            callback.onCompleted(ComplexResponse.fail(BORROW_BOOKS_REQUEST_IS_EXPIRED));
        }
        else if(!request.isAccept()){
            callback.onCompleted(ComplexResponse.fail(BORROW_BOOKS_REQUEST_IS_NOT_ACCEPT));
        }
        else {
            if(!borrowCardIsOfBorrower(request.getBorrower().getUserId(), borrowCardId)){
                callback.onCompleted(ComplexResponse.fail(BORROWCARD_IS_NOT_OF_BORROWER));
            }
            else{
                newMannerBorrowBooksUseCase.executeUseCase(new NewMannerBorrowBooksUseCase.RequestValues(librarianId, borrowCardId, request.getBookCopyIds()), response -> {
                    if (response.isSuccess()) {
                        assertNotNull(response.getPayload());
                        assertNotNull(response.getPayload().getLoan());
                        Loan loan = response.getPayload().getLoan();
                        request.confirm();
                        boolean success = borrowBooksRequestRepository.update(request);
                        if (success) {
                            callback.onCompleted(ComplexResponse.success(new ResponseValues(loan, request)));
                        } else {
                            loanRepository.deleteById(response.getPayload().getLoan().getLoanId());
                            callback.onCompleted(ComplexResponse.fail(CONFIRM_BORROW_BOOKS_REQUEST_FAIL_INTERNAL_DB_ERROR));
                        }
                    } else {
                        callback.onCompleted(ComplexResponse.fail(response.getMessage()));
                    }
                });
            }
        }
    }

    private boolean borrowCardIsOfBorrower(int borrowerId, int borrowCardId) {
        List<BorrowCard> borrowCards = borrowCardRepository.find(new FindBorrowCardByUserId(borrowerId));
        for(BorrowCard borrowCard : borrowCards){
            if(borrowCard.getBorrowCardId() == borrowCardId){
                return true;
            }
        }
        return false;
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int borrowCardId;
        private int requestId;
        private int librarianId;
        public int getRequestId() {
            return requestId;
        }

        public RequestValues(int borrowCardId, int requestId, int librarianId) {
            this.borrowCardId = borrowCardId;
            this.requestId = requestId;
            this.librarianId = librarianId;
        }

        public int getLibrarianId() {
            return librarianId;
        }

        public int getBorrowCardId() {
            return borrowCardId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private Loan loan;
        private BorrowBooksRequest request;
        public Loan getLoan() {
            return loan;
        }

        public BorrowBooksRequest getRequest() {
            return request;
        }

        public ResponseValues(Loan loan, BorrowBooksRequest request) {
            this.loan = loan;
            this.request = request;
        }
    }
}