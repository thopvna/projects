package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBookCopiesByKeyword;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FetchBookCopyUseCase extends UseCase<FetchBookCopyUseCase.RequestValues, FetchBookCopyUseCase.ResponseValues> {
    public static final String DONT_HAVE_ANY_BOOKCOPY_MATCHING = "Không tìm thấy Book Copy nào khớp.";
    private Repository<Integer, BookCopy> bookCopyRepository;

    @Autowired
    public FetchBookCopyUseCase(Repository<Integer, BookCopy> bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int bookCopyId = requestValues.getBookCopyId();
        BookCopy bookCopy = bookCopyRepository.fetchById(bookCopyId);
        if(bookCopy == null){
            callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_BOOKCOPY_MATCHING));
        }
        else{
            callback.onCompleted(ComplexResponse.success(new ResponseValues(bookCopy)));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int bookCopyId;

        public int getBookCopyId() {
            return bookCopyId;
        }

        public RequestValues(int bookCopyId) {
            this.bookCopyId = bookCopyId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private BookCopy bookCopy;

        public BookCopy getBookCopy() {
            return bookCopy;
        }

        public ResponseValues(BookCopy bookCopy) {
            this.bookCopy = bookCopy;
        }
    }
}