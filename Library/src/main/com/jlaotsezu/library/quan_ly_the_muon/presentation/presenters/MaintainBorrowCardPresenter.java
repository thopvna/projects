package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.*;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts.MaintainBorrowCardContract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MaintainBorrowCardPresenter implements MaintainBorrowCardContract.Presenter {
    public static final String LOAD_BORROW_CARDS_USECASE_ERROR_PAYLOAD_EMPTY = "Lỗi Load BorrowCards UseCase. Payload rỗng.";
    public static final String LOAD_BORROW_CARDS_DONT_HAVE_ANY_BORROWCARDS = "Lỗi Load BorrowCards. Không có BorrowCard nào.";
    public static final String DISABLE_BORROWCARD_SUCCESS = "Vô hiệu hóa thẻ mượn thành công.";
    public static final String DISABLE_BORROWCARDS_SUCCESS = "Vô hiệu hóa các thẻ mượn của người mượn thành công.";
    public static final String EXTEND_BORROWCARD_SUCCESS = "Gia hạn thẻ mượn thành công.";
    public static final String ACTIVE_BORROW_CARD_SUCCESS = "Active thẻ mượn thành công.";
    @Autowired
    FetchBorrowCardsUseCase fetchBorrowCardsUseCase;
    @Autowired
    DisableBorrowCardsUseCase disableBorrowCardsUseCase;
    @Autowired
    DisableBorrowCardUseCase disableBorrowCardUseCase;
    @Autowired
    ExtendBorrowCardUseCase extendBorrowCardUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    ActiveBorrowCardUseCase activeBorrowCardUseCase;
    @Autowired
    MaintainBorrowCardContract.Controller maintainBorrowCardController;
    @Override
    public void loadBorrowCards(String keyword) {
        useCaseHandler.execute(fetchBorrowCardsUseCase, new FetchBorrowCardsUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    maintainBorrowCardController.showError(LOAD_BORROW_CARDS_USECASE_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<BorrowCard> borrowCards = response.getPayload().getBorrowCards();
                    if(borrowCards == null || borrowCards.size() == 0){
                        maintainBorrowCardController.showError(LOAD_BORROW_CARDS_DONT_HAVE_ANY_BORROWCARDS);
                    }
                    else{
                        maintainBorrowCardController.showBorrowCards(borrowCards);
                    }
                }
            }
            else{
                maintainBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void disableBorrowCard(int borrowCardId) {
        useCaseHandler.execute(disableBorrowCardUseCase,  new DisableBorrowCardUseCase.RequestValues(borrowCardId), response -> {
            if(response.isSuccess()){
                maintainBorrowCardController.showMessage(DISABLE_BORROWCARD_SUCCESS);
            }
            else{
                maintainBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void disableBorrowCards(int borrowerId) {
        useCaseHandler.execute(disableBorrowCardsUseCase, new DisableBorrowCardsUseCase.RequestValues(borrowerId), response -> {
            if(response.isSuccess()){
                maintainBorrowCardController.showMessage(DISABLE_BORROWCARDS_SUCCESS);
            }
            else{
                maintainBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void extendBorrowCard(int borrowCardId) {
        useCaseHandler.execute(extendBorrowCardUseCase, new ExtendBorrowCardUseCase.RequestValues(borrowCardId), response -> {
            if(response.isSuccess()){
                maintainBorrowCardController.showMessage(EXTEND_BORROWCARD_SUCCESS);
            }
            else{
                maintainBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }
    public void activeBorrowCard(int borrowCardId){
        useCaseHandler.execute(activeBorrowCardUseCase, new ActiveBorrowCardUseCase.RequestValues(borrowCardId), response ->{
            if(response.isSuccess()) {
                maintainBorrowCardController.showMessage(ACTIVE_BORROW_CARD_SUCCESS);
            }
            else{
                maintainBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }
    @Override
    public void start() {

    }
}
