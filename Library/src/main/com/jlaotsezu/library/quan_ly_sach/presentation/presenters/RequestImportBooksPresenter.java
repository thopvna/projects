package main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.RequestImportBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.SearchBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.RequestImportBooksContract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class RequestImportBooksPresenter implements RequestImportBooksContract.Presenter {
    public static final String REQUEST_IMPORT_BOOKS_SUCCESS = "Thêm yêu cầu nhập sách thành công.";
    public static final String SEARCH_BOOK_USECASE_ERROR_PAYLOAD_EMTY = "Lỗi Search Book Usecase. Payload rỗng.";
    public static final String SEARCH_BOOK_USECASE_ERROR_DONT_HAVE_ANY_BOOKS = "Lỗi Search book Usecase. Không có book nào.";
    @Autowired
    RequestImportBooksUseCase requestImportBooksUseCase;
    @Autowired
    SearchBooksUseCase searchBooksUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    RequestImportBooksContract.Controller requestImportBooksController;
    @Override
    public void requestImportBooks(int librarianId, Map<Integer, Integer> booksAmount) {
        useCaseHandler.execute(requestImportBooksUseCase, new RequestImportBooksUseCase.RequestValues(booksAmount, librarianId), response -> {
            if(response.isSuccess()){
                requestImportBooksController.showMessage(REQUEST_IMPORT_BOOKS_SUCCESS);
                requestImportBooksController.clearRequestImportBooks();
            }
            else{
                requestImportBooksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void searchBooks(String keyword) {
        useCaseHandler.execute(searchBooksUseCase, new SearchBooksUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    requestImportBooksController.showError(SEARCH_BOOK_USECASE_ERROR_PAYLOAD_EMTY);
                }
                else{
                    List<Book> books = response.getPayload().getBooks();
                    if(books == null || books.size() == 0){
                        requestImportBooksController.showError(SEARCH_BOOK_USECASE_ERROR_DONT_HAVE_ANY_BOOKS);
                    }
                    else{
                        requestImportBooksController.showSearchResult(books);
                    }
                }
            }
            else{
                requestImportBooksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
