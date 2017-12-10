package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBookCopiesByKeyword;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchBookCopiesUseCase extends UseCase<SearchBookCopiesUseCase.RequestValues, SearchBookCopiesUseCase.ResponseValues> {
    public static final String DONT_HAVE_ANY_BOOKCOPY_MATCHING = "Không tìm thấy Book Copy nào khớp.";
    public static final String INPUT_INVALID_KEYWORD_EMPTY = "Từ khóa tìm kiếm không thể để trống.";
    private Repository<Integer, BookCopy> bookCopyRepository;

    @Autowired
    public SearchBookCopiesUseCase(Repository<Integer, BookCopy> bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();
        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_KEYWORD_EMPTY));
        }
        else {
            List<BookCopy> bookCopies = bookCopyRepository.find(new FindBookCopiesByKeyword(keyword));
            if(bookCopies == null || bookCopies.size() == 0){
                callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_BOOKCOPY_MATCHING));
            }
            else {
                callback.onCompleted(ComplexResponse.success(new ResponseValues(bookCopies)));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public RequestValues(String keyword) {
            this.keyword = keyword;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<BookCopy> bookCopies;

        public List<BookCopy> getBookCopies() {
            return bookCopies;
        }

        public ResponseValues(List<BookCopy> bookCopies) {
            this.bookCopies = bookCopies;
        }
    }
}