package main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class ExtendBorrowCardUseCase extends UseCase<ExtendBorrowCardUseCase.RequestValues, ExtendBorrowCardUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_BORROW_CARD_MATCHING = "Không có thẻ mượn nào khớp.";
    public static final String EXTEND_BORROW_CARD_FAILED_INTERNAL_DB_ERROR = "Lỗi cơ sở dữ liệu.";
    public static final String BORROW_CARD_IS_NOT_EXPIRED = "Thẻ mượn vẫn chưa hết hạn.";
    public static final String BORROW_CARD_IS_DISABLED = "Thẻ mượn đang bị Vô hiệu hóa.";
    private Repository<Integer, BorrowCard> borrowCardRepository;

    @Autowired
    public ExtendBorrowCardUseCase(Repository<Integer, BorrowCard> borrowCardRepository) {
        this.borrowCardRepository = borrowCardRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int borrowCardId = requestValues.getBorrowCardId();
        BorrowCard borrowCard = borrowCardRepository.fetchById(borrowCardId);
        if(borrowCard == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_BORROW_CARD_MATCHING));
        }
        else{
            if(!borrowCard.isActive()){
                callback.onCompleted(ComplexResponse.fail(BORROW_CARD_IS_DISABLED));
            }
            else if(!borrowCard.isExpired()){
                callback.onCompleted(ComplexResponse.fail(BORROW_CARD_IS_NOT_EXPIRED));
            }
            else{
                borrowCard.extend();
                boolean success = borrowCardRepository.update(borrowCard);
                if(success)
                    callback.onCompleted(ComplexResponse.success());
                else
                    callback.onCompleted(ComplexResponse.fail(EXTEND_BORROW_CARD_FAILED_INTERNAL_DB_ERROR));
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