package main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;

import java.util.List;

public interface BooksContract {
    interface Controller extends BaseController {
        void showBooks(List<Book> books);
        void showSearchResult(List<Book> books);
    }
    interface Presenter extends BasePresenter{
        void loadBooks();
        void searchBooks(String keyword);
    }
}
