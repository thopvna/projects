package main.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class CancelBookImportRequestUseCase extends UseCase<CancelBookImportRequestUseCase.RequestValues, CancelBookImportRequestUseCase.ResponseValues> {
    public static final String BOOK_IMPORT_REQUEST_NOT_EXISTING = "Yêu cầu nhập sách không tồn tại.";
    public static final String BOOK_IMPORT_REQUEST_ADREADY_CONFIRMED = "Yêu cầu nhập sách đã được confirm rồi.";
    public static final String INTERNAL_DATABASE_ERROR = "Lỗi cơ sở dữ liệu.";
    private Repository<Integer, BookImportRequest> bookImportRequestRepository;

    @Autowired
    public CancelBookImportRequestUseCase(Repository<Integer, BookImportRequest> bookImportRequestRepository) {
        this.bookImportRequestRepository = bookImportRequestRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int requestId = requestValues.getRequestId();
        BookImportRequest request = bookImportRequestRepository.fetchById(requestId);
        if(request == null){
            callback.onCompleted(ComplexResponse.fail(BOOK_IMPORT_REQUEST_NOT_EXISTING));
        }
        else if(request.isConfirm()){
            callback.onCompleted(ComplexResponse.fail(BOOK_IMPORT_REQUEST_ADREADY_CONFIRMED));
        }
        else{
            boolean success = bookImportRequestRepository.deleteById(requestId);
            if(success){
                callback.onCompleted(ComplexResponse.success());
            }
            else{
                callback.onCompleted(ComplexResponse.fail(INTERNAL_DATABASE_ERROR));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int requestId;

        public int getRequestId() {
            return requestId;
        }

        public RequestValues(int requestId) {
            this.requestId = requestId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}