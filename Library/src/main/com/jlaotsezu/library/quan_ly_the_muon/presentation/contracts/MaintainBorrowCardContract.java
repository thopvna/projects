package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;

import java.util.List;

public interface MaintainBorrowCardContract {
    interface Presenter extends BasePresenter {
        void loadBorrowCards(String keyword);
        void disableBorrowCard(int borrowCardId);
        void disableBorrowCards(int borrowerId);
        void extendBorrowCard(int borrowCardId);
        void activeBorrowCard(int borrowCardId);
    }
    interface Controller extends BaseController {
        void showBorrowCards(List<BorrowCard> cards);
    }
}
