package main.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoadBooksUseCase extends UseCase<LoadBooksUseCase.RequestValues, LoadBooksUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_BOOKS = "Không có Book nào.";
    private Repository<Integer, Book> bookRepository;
    @Autowired
    public LoadBooksUseCase(Repository<Integer, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<Book> books = bookRepository.fetchAll();
        if(books == null || books.size() == 0){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_BOOKS));
        }
        else{
            callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<Book> books;

        public ResponseValues(List<Book> books) {
            this.books = books;
        }

        public List<Book> getBooks() {
            return books;
        }
    }
}