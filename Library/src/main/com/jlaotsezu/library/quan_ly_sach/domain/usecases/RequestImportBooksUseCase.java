package main.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.Role;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookAmount;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestImportBooksUseCase extends UseCase<RequestImportBooksUseCase.RequestValues, RequestImportBooksUseCase.ResponseValues> {
    public static final String INPUT_INVALID_BOOK_AMOUNT_INVALID = "Thông tin sách đầu vào không hợp lệ(Rỗng).";
    public static final String SOME_BOOKS_DON_T_EXISTING_UNABLE_REQUEST_IMPORT_NON_EXISTING_BOOKS = "Một vài cuốn sách không tồn tại. Không thể request import book không tồn tại.";
    public static final String REQUEST_IMPORT_BOOKS_FAILED_DB_ERROR = "Lỗi cơ sở dữ liệu";
    public static final String DON_T_HAVE_ANY_LIBRARIAN_MATCHING = "Không có thủ thư nào khớp.";
    public static final String USER_WITH_LIBRIAN_ID_IS_NOT_A_LIBRARIAN = "Người dùng hiện tại không phải Librarian.";
    private Repository<Integer, BookImportRequest> bookImportRequestRepository;
    private Repository<Integer, Book> bookRepository;
    private Repository<Integer, User> userRepository;
    @Autowired
    public RequestImportBooksUseCase(Repository<Integer, BookImportRequest> bookImportRequestRepository, Repository<Integer, Book> bookRepository, Repository<Integer, User> userRepository) {
        this.bookImportRequestRepository = bookImportRequestRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        Map<Integer, Integer> booksAmount = requestValues.getBooksAmount();
        int librarianId = requestValues.getLibrarianId();
        User librarian = userRepository.fetchById(librarianId);
        if(librarian == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_LIBRARIAN_MATCHING));
        }
        else if(!librarian.getRoles().contains(Role.LIBRARICIAN)){
            callback.onCompleted(ComplexResponse.fail(USER_WITH_LIBRIAN_ID_IS_NOT_A_LIBRARIAN));
        }
        else if(booksAmount == null || booksAmount.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_BOOK_AMOUNT_INVALID));
        }
        else{
            List<BookAmount> bookAmounts = new LinkedList<>();
            for(int bookId : booksAmount.keySet()){
                Book book = bookRepository.fetchById(bookId);
                if(book == null){
                    callback.onCompleted(ComplexResponse.fail(SOME_BOOKS_DON_T_EXISTING_UNABLE_REQUEST_IMPORT_NON_EXISTING_BOOKS));
                    return;
                }
                else {
                    int amount = booksAmount.get(bookId);
                    bookAmounts.add(new BookAmount(book, amount));
                }
            }
            BookImportRequest request = new BookImportRequest(librarian, bookAmounts);
            boolean success = bookImportRequestRepository.save(request);

            if(!success){
                callback.onCompleted(ComplexResponse.fail(REQUEST_IMPORT_BOOKS_FAILED_DB_ERROR));
            }
            else
                callback.onCompleted(ComplexResponse.success());
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private Map<Integer, Integer> booksAmount;

        public RequestValues(Map<Integer, Integer> booksAmount, int librarianId) {
            this.booksAmount = booksAmount;
            this.librarianId = librarianId;
        }

        public int getLibrarianId() {
            return librarianId;
        }

        private int librarianId;

        public Map<Integer, Integer> getBooksAmount() {
            return booksAmount;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}