package main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import main.com.jlaotsezu.library.support.communicate.FailedResponse;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByUserName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Chứa login nghiệp vụ cho Use case thực hiện việc login
 */

public class LoginUseCase extends UseCase<LoginUseCase.RequestValues, LoginUseCase.ResponseValues> {

    public static final String USERNAME_IS_UNAVAILABLE = "Tên người dùng không hợp lệ.";
    public static final String PASSWORD_IS_UNAVAILABLE = "Mật khẩu không hợp lệ.";
    public static final String USER_IS_NOT_EXISTING = "Người dùng không tồn tại.";
    public static final String PASSWORD_IS_WRONG = "Mật khẩu không chính xác.";

    @Autowired
    public LoginUseCase(Repository<Integer, User> userRepository) {
        this.userRepository = userRepository;
    }

    private Repository<Integer, User> userRepository;

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String userName = requestValues.getUserName();
        String password = requestValues.getPassword();
        
        if(userName == null || userName.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(USERNAME_IS_UNAVAILABLE));
        }
        else if(password == null || password.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(PASSWORD_IS_UNAVAILABLE));
        }
        else {
            Specification findUserByUserName = new FindUserByUserName(userName);
            List<User> users = userRepository.find(findUserByUserName);
            if(users == null || users.size() == 0){
                callback.onCompleted(ComplexResponse.fail(USER_IS_NOT_EXISTING));
            }
            else{
                User user = users.get(0);
                if (user.getPassword().equalsIgnoreCase(password)) {
                    callback.onCompleted(ComplexResponse.success(new ResponseValues(user)));
                } else
                    callback.onCompleted(new FailedResponse<>(PASSWORD_IS_WRONG));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private String userName;
        private String password;
        public RequestValues(String userName, String password){
            this.userName = userName;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public String getUserName() {
            return userName;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
        private User user;

        public ResponseValues(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}
