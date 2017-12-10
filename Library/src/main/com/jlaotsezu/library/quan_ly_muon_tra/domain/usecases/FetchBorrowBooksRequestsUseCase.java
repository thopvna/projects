package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByKeyword;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBorrowBooksRequestsByUserId;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class FetchBorrowBooksRequestsUseCase extends UseCase<FetchBorrowBooksRequestsUseCase.RequestValues, FetchBorrowBooksRequestsUseCase.ResponseValues> {
    public static final String DONT_HAVE_ANY_USERS = "Không tìm thấy người dùng nào khớp.";
    public static final String DONT_HAVE_ANY_BORROW_BOOKS_REQUESTS = "Không tìm thấy Borrow Books Request nào khớp.";
    public static final String INPUT_INVALID_KEYWORD_EMPTY = "Từ khóa tìm kiếm không thể bỏ trống.";
    private Repository<Integer, User> userRepository;
    private Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository;
    @Autowired
    public FetchBorrowBooksRequestsUseCase(Repository<Integer, User> userRepository, Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository) {
        this.userRepository = userRepository;
        this.borrowBooksRequestRepository = borrowBooksRequestRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();
        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_KEYWORD_EMPTY));
        }
        else {
            List<User> users = userRepository.find(new FindUserByKeyword(keyword));
            if (users == null || users.size() == 0) {
                callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_USERS));
            } else {
                List<BorrowBooksRequest> borrowBooksRequests = new LinkedList<>();
                for (User user : users) {
                    List<BorrowBooksRequest> pieces = borrowBooksRequestRepository.find(new FindBorrowBooksRequestsByUserId(user.getUserId()));
                    if (pieces != null && pieces.size() > 0) {
                        borrowBooksRequests.addAll(pieces);
                    }

                }
                if (borrowBooksRequests.size() == 0) {
                    callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_BORROW_BOOKS_REQUESTS));
                } else {
                    callback.onCompleted(ComplexResponse.success(new ResponseValues(borrowBooksRequests)));
                }
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public RequestValues(String keyword) {
            this.keyword = keyword;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<BorrowBooksRequest> borrowBooksRequests;

        public List<BorrowBooksRequest> getBorrowBooksRequests() {
            return borrowBooksRequests;
        }

        public ResponseValues(List<BorrowBooksRequest> borrowBooksRequests) {
            this.borrowBooksRequests = borrowBooksRequests;
        }
    }
}