package main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.LoadBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.SearchBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.BooksContract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BooksPresenter implements BooksContract.Presenter {
    public static final String LOAD_BOOKS_USECASE_ERROR_PAYLOAD_EMPTY = "Lỗi Load Books Usecase, Payload rỗng.";
    public static final String LOAD_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOK = "Lỗi Load Books Usecase. Không có cuốn sách nào.";
    public static final String SEARCH_BOOKS_USECASE_ERROR_PAYLOAD_EMPTY = "Lỗi SearchBooks Usecase, Payload rỗng.";
    public static final String SEARCH_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOKS = "Lỗi Search Books Usecase. Không có sách nào.";
    @Autowired
    LoadBooksUseCase loadBooksUseCase;
    @Autowired
    SearchBooksUseCase searchBooksUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    BooksContract.Controller booksController;
    @Override
    public void loadBooks() {
        useCaseHandler.execute(loadBooksUseCase, new LoadBooksUseCase.RequestValues(), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    booksController.showError(LOAD_BOOKS_USECASE_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<Book> books = response.getPayload().getBooks();
                    if(books == null || books.size() == 0){
                        booksController.showError(LOAD_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOK);
                    }
                    else{
                        booksController.showBooks(books);
                    }
                }
            }
            else{
                booksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void searchBooks(String keyword) {
        useCaseHandler.execute(searchBooksUseCase, new SearchBooksUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    booksController.showError(SEARCH_BOOKS_USECASE_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<Book> books = response.getPayload().getBooks();
                    if(books == null || books.size() == 0){
                        booksController.showError(SEARCH_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOKS);
                    }
                    else{
                        booksController.showSearchResult(books);
                    }
                }
            }
            else{
                booksController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
