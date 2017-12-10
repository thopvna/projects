package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindNonReturnLoanByUserId;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByKeyword;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindLoanByUserId;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class LoadNonReturnLoansUseCase extends UseCase<LoadNonReturnLoansUseCase.RequestValues, LoadNonReturnLoansUseCase.ResponseValues> {
    public static final String DONT_HAVE_ANY_USER_MATCHING = "Không tìm thấy người dùng nào khớp.";
    public static final String DONT_HAVE_ANY_NON_RETURN_LOANS = "Người dùng không có Loan nào chưa trả.";
    public static final String INPUT_INVALID_KEYWORD_EMPTY = "Từ khóa tìm kiếm không thể để trống.";

    @Autowired
    public LoadNonReturnLoansUseCase(Repository<Integer, Loan> loanRepository, Repository<Integer, User> userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    private Repository<Integer, Loan> loanRepository;
    private Repository<Integer, User> userRepository;

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();
        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_KEYWORD_EMPTY));
        }
        else {
            List<User> users = userRepository.find(new FindUserByKeyword(keyword));
            if (users == null || users.size() == 0) {
                callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_USER_MATCHING));
            } else {
                List<Loan> loans = new LinkedList<>();
                for (User user : users) {
                    List<Loan> pieces = loanRepository.find(new FindNonReturnLoanByUserId(user.getUserId()));
                    if (pieces != null && pieces.size() > 0) {
                        loans.addAll(pieces);
                    }
                }
                if (loans.size() == 0) {
                    callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_NON_RETURN_LOANS));
                } else {
                    callback.onCompleted(ComplexResponse.success(new ResponseValues(loans)));
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
        private List<Loan> loans;

        public List<Loan> getLoans() {
            return loans;
        }

        public ResponseValues(List<Loan> loans) {
            this.loans = loans;
        }
    }
}