package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindNonConfirmBorrowBooksRequests;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoadAllBorrowBooksRequestsUseCase extends UseCase<LoadAllBorrowBooksRequestsUseCase.RequestValues, LoadAllBorrowBooksRequestsUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_BORROW_BOOKS_REQUESTS = "Không có bất kỳ borrow books request nào.";
    private Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository;
    @Autowired
    public LoadAllBorrowBooksRequestsUseCase(Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository) {
        this.borrowBooksRequestRepository = borrowBooksRequestRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<BorrowBooksRequest> borrowBooksRequests = borrowBooksRequestRepository.find(new FindNonConfirmBorrowBooksRequests());
        if(borrowBooksRequests == null || borrowBooksRequests.size() == 0){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_BORROW_BOOKS_REQUESTS));
        }
        else{
            callback.onCompleted(ComplexResponse.success(new ResponseValues(borrowBooksRequests)));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<BorrowBooksRequest> borrowBooksRequests;

        public ResponseValues(List<BorrowBooksRequest> borrowBooksRequests) {
            this.borrowBooksRequests = borrowBooksRequests;
        }

        public List<BorrowBooksRequest> getBorrowBooksRequests() {
            return borrowBooksRequests;
        }
    }
}