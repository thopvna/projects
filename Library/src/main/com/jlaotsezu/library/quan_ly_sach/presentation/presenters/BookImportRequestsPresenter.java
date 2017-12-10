package main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.CancelBookImportRequestUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.LoadBookImportRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.BookImportRequestsContract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookImportRequestsPresenter  implements BookImportRequestsContract.Presenter{
    public static final String LOAD_BOOK_IMPORT_REQUESTS_ERROR_PAYLOAD_EMPTY = "Lỗi Usecase Load Book Import Requests. Payload rỗng.";
    public static final String LOAD_BOOK_IMPORT_REQUESTS_ERROR_DONT_HAVE_ANY_REQUESTS = "Lỗi Load Book Import Requests Usecase. Không có request nào.";
    public static final String CANCEL_REQUEST_SUCCESS = "Hủy bỏ yêu cầu nhập sách thành công.";
    @Autowired
    LoadBookImportRequestsUseCase loadBookImportRequestsUseCase;
    @Autowired
    CancelBookImportRequestUseCase cancelBookImportRequestUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    BookImportRequestsContract.Controller bookImportRequestsController;
    @Override
    public void loadBookImportRequests(int librarianId) {
        useCaseHandler.execute(loadBookImportRequestsUseCase, new LoadBookImportRequestsUseCase.RequestValues(librarianId), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    bookImportRequestsController.showError(LOAD_BOOK_IMPORT_REQUESTS_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<BookImportRequest> requests = response.getPayload().getBookImportRequests();
                    if(requests == null || requests.size() == 0){
                        bookImportRequestsController.showError(LOAD_BOOK_IMPORT_REQUESTS_ERROR_DONT_HAVE_ANY_REQUESTS);
                    }
                    else{
                        bookImportRequestsController.showBookImportRequests(requests);
                    }
                }
            }
            else{
                bookImportRequestsController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void cancelRequest(int requestId) {
        useCaseHandler.execute(cancelBookImportRequestUseCase, new CancelBookImportRequestUseCase.RequestValues(requestId), response -> {
            if(response.isSuccess()) {
                bookImportRequestsController.showMessage(CANCEL_REQUEST_SUCCESS);
                bookImportRequestsController.removeBookImportRequest(requestId);
            }
            else{
                bookImportRequestsController.showError("Hủy bỏ yêu cầu nhập sách thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
