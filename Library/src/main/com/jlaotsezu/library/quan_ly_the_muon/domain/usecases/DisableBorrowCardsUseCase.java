package main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBorrowCardByUserId;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Disable all borrow card of user
 */

public class DisableBorrowCardsUseCase extends UseCase<DisableBorrowCardsUseCase.RequestValues, DisableBorrowCardsUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_USER_MATCHING = "Không có người dùng nào khớp.";
    public static final String USER_DON_T_HAVE_ANY_BORROW_CARDS = "Người dùng không có thẻ mượn nào.";
    public static final String DISABLE_BORROW_CARDS_FAILED_INTERNAL_DB_ERROR = "Lỗi cơ sở dữ liệu.";
    private Repository<Integer, BorrowCard> borrowCardRepository;
    private Repository<Integer, User> userRepository;

    @Autowired
    public DisableBorrowCardsUseCase(Repository<Integer, BorrowCard> borrowCardRepository, Repository<Integer, User> userRepository) {
        this.borrowCardRepository = borrowCardRepository;
        this.userRepository = userRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int userId = requestValues.getUserId();
        User user = userRepository.fetchById(userId);
        if(user == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_USER_MATCHING));
        }
        else{
            List<BorrowCard> borrowCards = borrowCardRepository.find(new FindBorrowCardByUserId(userId));
            if(borrowCards == null || borrowCards.size() == 0){
                callback.onCompleted(ComplexResponse.fail(USER_DON_T_HAVE_ANY_BORROW_CARDS));
            }
            else{
                for(BorrowCard borrowCard : borrowCards){
                    borrowCard.deActive();
                }
                boolean success = borrowCardRepository.updateAll(borrowCards);
                if(success){
                    callback.onCompleted(ComplexResponse.success());
                }
                else
                    callback.onCompleted(ComplexResponse.fail(DISABLE_BORROW_CARDS_FAILED_INTERNAL_DB_ERROR));
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

    }
}