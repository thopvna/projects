package main.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.specifications.FindBookByKeyword;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Chứa logic nghiệp vụ để thực hiện use case tìm kiếm sách
 */

public class SearchBooksUseCase extends UseCase<SearchBooksUseCase.RequestValues, SearchBooksUseCase.ResponseValues> {
    public static final String KEYWORD_IS_UNAVAILABLE = "Từ khóa không hợp lệ.";
    public static final String DON_T_HAVE_ANY_BOOK_MATCHING = "Không có sách nào khớp.";
    private Repository<Integer, Book> bookRepository;

    @Autowired
    public SearchBooksUseCase(Repository<Integer, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();

        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(KEYWORD_IS_UNAVAILABLE));
        }
        else {
            Specification findBookByCriteriaAndKeyword = new FindBookByKeyword(keyword);
            List<Book> books = bookRepository.find(findBookByCriteriaAndKeyword);
            if(books == null || books.size() == 0){
                callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_BOOK_MATCHING));
            }
            else{
                callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues{
        private String keyword;

        public RequestValues(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
        private List<Book> books;

        public ResponseValues(List<Book> books) {
            this.books = books;
        }

        public List<Book> getBooks() {
            return books;
        }
    }
}
