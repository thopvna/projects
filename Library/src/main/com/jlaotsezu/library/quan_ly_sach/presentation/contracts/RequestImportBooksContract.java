package main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;

import java.util.List;
import java.util.Map;

public interface RequestImportBooksContract {
    interface Controller extends BaseController{
        void showSearchResult(List<Book> books);
        void clearRequestImportBooks();
    }
    interface Presenter extends BasePresenter{
        void requestImportBooks(int librarianId, Map<Integer, Integer> booksAmount);
        void searchBooks(String keyword);
    }
}
