package main.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByKeyword;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class FindBorrowerUseCase extends UseCase<FindBorrowerUseCase.RequestValues, FindBorrowerUseCase.ResponseValues> {
    public static final String DONT_HAVE_ANY_USERS_MATCHING = "Không tìm thấy người dùng nào khớp.";
    public static final String INPUT_INVALID_KEYWORD_IS_EMPTY = "Đầu vào không hợp lệ. Từ khóa không thể để trống.";
    public static final String DONT_HAVE_ANY_BORROWERS_MATCHING = "Không tìm thấy người Mượn nào khớp.";
    private Repository<Integer, User> userRepository;

    @Autowired
    public FindBorrowerUseCase(Repository<Integer, User> userRepository) {
        this.userRepository = userRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();
        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_KEYWORD_IS_EMPTY));
        }
        else{
            List<User> users = userRepository.find(new FindUserByKeyword(keyword));
            if(users == null || users.size() == 0){
                callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_USERS_MATCHING));
            }
            else{
                List<User> borrowers = new LinkedList<>();
                for(User user : users){
                    if(user.isBorrower())
                        borrowers.add(user);
                }
                if(borrowers.size() == 0){
                    callback.onCompleted(ComplexResponse.fail(DONT_HAVE_ANY_BORROWERS_MATCHING));
                }
                else{
                    callback.onCompleted(ComplexResponse.success(new ResponseValues(borrowers)));
                }
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
        private List<User> borrowers;

        public List<User> getBorrowers() {
            return borrowers;
        }

        public ResponseValues(List<User> borrowers) {
            this.borrowers = borrowers;
        }
    }
}