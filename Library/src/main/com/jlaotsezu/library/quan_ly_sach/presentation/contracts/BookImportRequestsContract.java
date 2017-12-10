package main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;

import java.util.List;

public interface BookImportRequestsContract {
    interface Controller extends BaseController{
        void showBookImportRequests(List<BookImportRequest> requests);
        void removeBookImportRequest(int requestId);
    }
    interface Presenter extends BasePresenter{
        void loadBookImportRequests(int librarianId);
        void cancelRequest(int requestId);
    }
}
