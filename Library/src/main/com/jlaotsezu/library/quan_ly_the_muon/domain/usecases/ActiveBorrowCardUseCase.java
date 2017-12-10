package main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class ActiveBorrowCardUseCase extends UseCase<ActiveBorrowCardUseCase.RequestValues, ActiveBorrowCardUseCase.ResponseValues> {
    public static final String DATABASE_INTERNAL_ERROR = "Lỗi cơ sở dữ liệu.";
    public static final String BORROW_CARD_ALREADY_ACTIVE = "Thẻ mượn vẫn đang Active.";
    public static final String BORROW_CARD_IS_NOT_EXISTING = "Thẻ mượn không tồn tại.";
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Autowired
    public ActiveBorrowCardUseCase(Repository<Integer, BorrowCard> borrowCardRepository) {
        this.borrowCardRepository = borrowCardRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int borrowCardId = requestValues.getBorrowCardId();
        BorrowCard borrowCard = borrowCardRepository.fetchById(borrowCardId);
        if(borrowCard == null){
            callback.onCompleted(ComplexResponse.fail(BORROW_CARD_IS_NOT_EXISTING));
        }
        else if(borrowCard.isActive()){
            callback.onCompleted(ComplexResponse.fail(BORROW_CARD_ALREADY_ACTIVE));
        }
        else {
            borrowCard.active();
            boolean success = borrowCardRepository.update(borrowCard);
            if(success) {
                callback.onCompleted(ComplexResponse.success());
            }
            else{
                callback.onCompleted(ComplexResponse.fail(DATABASE_INTERNAL_ERROR));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int borrowCardId;

        public int getBorrowCardId() {
            return borrowCardId;
        }

        public RequestValues(int borrowCardId) {
            this.borrowCardId = borrowCardId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}