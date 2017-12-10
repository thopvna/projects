package com.thopv.projects.libraryforreader.home.presentation.contracts;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;

import java.util.List;

/**
 * Created by thopv on 12/3/2017.
 */

public interface CartContract {
    interface View extends BaseBookContract.View{
        void showCartBooks(List<Book> books);
        void enableSendBorrowRequestButton();
        void disableSendBorrowRequestButton();
        void showSendBorrowRequestSuccess();
        void clearCart();
        void showNoCartBooks();
        void hideNoCartBooks();
    }
    interface Presenter extends BaseBookContract.Presenter{
        void loadCartBooks();
        void sendBorrowRequest();
    }
}
