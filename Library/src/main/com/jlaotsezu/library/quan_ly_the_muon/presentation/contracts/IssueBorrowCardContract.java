package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;

import java.util.List;


public interface IssueBorrowCardContract {
    interface Presenter extends BasePresenter{
        void findBorrower(String keyword);
        void issueBorrowCard(int borrowerId);
    }
    interface Controller extends BaseController {
        void showBorrowers(List<User> borrowers);
        void showBorrowCard(BorrowCard borrowCard);
    }
}
