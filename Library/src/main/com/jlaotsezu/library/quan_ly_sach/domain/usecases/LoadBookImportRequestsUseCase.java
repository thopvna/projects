package main.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.Role;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.specifications.FindBookImportRequestByLibrarianId;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoadBookImportRequestsUseCase extends UseCase<LoadBookImportRequestsUseCase.RequestValues, LoadBookImportRequestsUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_LIBRARIAN_MATCHING = "Thủ thư không tồn tại.";
    public static final String CURRENT_USER_IS_NOT_A_LIBRARIAN = "Người dùng hiện tại không phải Thủ thư.";
    public static final String LIBRARIAN_DON_T_HAVE_ANY_BOOK_IMPORT_REQUEST = "Thủ thư không có Book import requests nào.";
    private Repository<Integer, BookImportRequest> bookImportRequestRepository;
    private Repository<Integer, User> userRepository;
    @Autowired
    public LoadBookImportRequestsUseCase(Repository<Integer, BookImportRequest> bookImportRequestRepository, Repository<Integer, User> userRepository) {
        this.bookImportRequestRepository = bookImportRequestRepository;
        this.userRepository = userRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int librarianId = requestValues.getLibrarianId();
        User librarian = userRepository.fetchById(librarianId);
        if(librarian == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_LIBRARIAN_MATCHING));
        }
        else if(librarian.getRoles() == null || !librarian.getRoles().contains(Role.LIBRARICIAN)){
            callback.onCompleted(ComplexResponse.fail(CURRENT_USER_IS_NOT_A_LIBRARIAN));
        }
        else{
            List<BookImportRequest> requests = bookImportRequestRepository.find(new FindBookImportRequestByLibrarianId(librarianId));
            if(requests == null || requests.size() == 0){
                callback.onCompleted(ComplexResponse.fail(LIBRARIAN_DON_T_HAVE_ANY_BOOK_IMPORT_REQUEST));
            }
            else{
                callback.onCompleted(ComplexResponse.success(new ResponseValues(requests)));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int librarianId;

        public int getLibrarianId() {
            return librarianId;
        }

        public RequestValues(int librarianId) {
            this.librarianId = librarianId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<BookImportRequest> bookImportRequests;

        public List<BookImportRequest> getBookImportRequests() {
            return bookImportRequests;
        }

        public ResponseValues(List<BookImportRequest> bookImportRequests) {
            this.bookImportRequests = bookImportRequests;
        }
    }
}