package main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBorrowCardByUserId;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class IssueBorrowCardUseCase extends UseCase<IssueBorrowCardUseCase.RequestValues, IssueBorrowCardUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_USER_MATCHING = "Không có người dùng nào khớp.";
    public static final String USER_ADREADY_HAVE_BORROW_CARDS = "Người dùng đã có thẻ.";
    public static final String ISSUE_BORROW_CARD_FAILED_INTERNAL_DB_ERROR = "Lỗi cơ sở dữ liệu.";

    private final Repository<Integer, User> userRepository;
    private final Repository<Integer, BorrowCard> borrowCardRepository;

    @Autowired
    public IssueBorrowCardUseCase( @Qualifier(value = "userRepository") Repository<Integer, User> userRepository, @Qualifier(value = "borrowCardRepository") Repository<Integer, BorrowCard> borrowCardRepository) {
        this.userRepository = userRepository;
        this.borrowCardRepository = borrowCardRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int userId = requestValues.getUserId();
        User user = userRepository.fetchById(userId);
        if(user == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_USER_MATCHING));
        }
        else{
            List<BorrowCard> borrowCards = borrowCardRepository.find(new FindBorrowCardByUserId(userId));
            if(borrowCards != null && borrowCards.size() > 0){
                callback.onCompleted(ComplexResponse.fail(USER_ADREADY_HAVE_BORROW_CARDS));
            }
            else{
                BorrowCard borrowCard = new BorrowCard.Builder()
                        .setUser(user)
                        .build();
                boolean success = borrowCardRepository.save(borrowCard);
                if(success)
                    callback.onCompleted(ComplexResponse.success(new ResponseValues(borrowCard)));
                else
                    callback.onCompleted(ComplexResponse.fail(ISSUE_BORROW_CARD_FAILED_INTERNAL_DB_ERROR));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int userId;

        public int getUserId() {
            return userId;
        }

        public RequestValues(int userId) {
            this.userId = userId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private BorrowCard borrowCard;

        public BorrowCard getBorrowCard() {
            return borrowCard;
        }

        public ResponseValues(BorrowCard borrowCard) {
            this.borrowCard = borrowCard;
        }
    }
}