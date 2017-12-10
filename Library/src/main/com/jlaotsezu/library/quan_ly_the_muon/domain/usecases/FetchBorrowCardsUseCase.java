package main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.specifications.FindBorrowCardByKeyword;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Fetch borrow cards of user;
 */

public class FetchBorrowCardsUseCase extends UseCase<FetchBorrowCardsUseCase.RequestValues, FetchBorrowCardsUseCase.ResponseValues> {
    public static final String USER_DON_T_HAVE_ANY_BORROW_CARDS = "Người dùng không có thẻ mượn nào.";
    public static final String INPUT_INVALID_KEYWORD_EMPTY = "Từ khóa tìm kiếm không thể để trống.";
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Autowired
    public FetchBorrowCardsUseCase(Repository<Integer, BorrowCard> borrowCardRepository) {
        this.borrowCardRepository = borrowCardRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();
        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_KEYWORD_EMPTY));
        }
        else {
            List<BorrowCard> borrowCards = borrowCardRepository.find(new FindBorrowCardByKeyword(keyword));
            if (borrowCards == null || borrowCards.size() == 0) {
                callback.onCompleted(ComplexResponse.fail(USER_DON_T_HAVE_ANY_BORROW_CARDS));
            } else {
                callback.onCompleted(ComplexResponse.success(new ResponseValues(borrowCards)));
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
        private List<BorrowCard> borrowCards;

        public List<BorrowCard> getBorrowCards() {
            return borrowCards;
        }

        public ResponseValues(List<BorrowCard> borrowCards) {
            this.borrowCards = borrowCards;
        }
    }
}