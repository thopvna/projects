package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.FindBorrowerUseCase;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.IssueBorrowCardUseCase;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts.IssueBorrowCardContract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IssueBorrowCardPresenter implements IssueBorrowCardContract.Presenter {
    public static final String FIND_BORROWER_USECASE_ERROR_PAYLOAD_EMPTY = "Lỗi Find Borrower Usecase. Payload rỗng.";
    public static final String FIND_BORROWER_USECASE_ERROR_DONT_HAVE_ANY_BORROWER = "Lỗi Find Borrower Usecase. Không có người mượn nào.";
    public static final String ISSUE_BORROW_CARD_USECASE_ERROR_PAYLOAD_EMPTY = "Lỗi Issue BorrowCard Usecase. Payload rỗng.";
    public static final String ISSUE_BORROW_CARD_USECASE_ERROR_BORROW_CARD_IS_NULL = "Lỗi Issue BorrowCard Usecase. BorrowCard bị trống.";
    public static final String FIND_BORROWER_USECASE_ERROR_USER_ISNOT_BORROWER = "Lỗi Find Borrower Usecase. Người dùng không phải Borrower.";
    @Autowired
    FindBorrowerUseCase findBorrowerUseCase;
    @Autowired
    IssueBorrowCardUseCase issueBorrowCardUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    IssueBorrowCardContract.Controller issueBorrowCardController;
    @Override
    public void findBorrower(String keyword) {
        useCaseHandler.execute(findBorrowerUseCase, new FindBorrowerUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    issueBorrowCardController.showError(FIND_BORROWER_USECASE_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<User> borrowers = response.getPayload().getBorrowers();
                    if(borrowers == null || borrowers.size() == 0){
                        issueBorrowCardController.showError(FIND_BORROWER_USECASE_ERROR_DONT_HAVE_ANY_BORROWER);
                    }
                    else{
                        for(User borrower : borrowers){
                            if(!borrower.isBorrower()){
                                issueBorrowCardController.showError(FIND_BORROWER_USECASE_ERROR_USER_ISNOT_BORROWER);
                                return;
                            }
                        }
                        issueBorrowCardController.showBorrowers(borrowers);
                    }
                }
            }
            else{
                issueBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void issueBorrowCard(int borrowerId) {
        useCaseHandler.execute(issueBorrowCardUseCase, new IssueBorrowCardUseCase.RequestValues(borrowerId), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    issueBorrowCardController.showError(ISSUE_BORROW_CARD_USECASE_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    BorrowCard borrowCard = response.getPayload().getBorrowCard();
                    if(borrowCard == null){
                        issueBorrowCardController.showError(ISSUE_BORROW_CARD_USECASE_ERROR_BORROW_CARD_IS_NULL);
                    }
                    else{
                        issueBorrowCardController.showBorrowCard(borrowCard);
                    }
                }
            }
            else{
                issueBorrowCardController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
