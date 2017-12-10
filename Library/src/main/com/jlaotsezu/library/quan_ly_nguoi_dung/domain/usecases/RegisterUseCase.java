package main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByUserName;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Chứa login nghiệp vụ cho Use case thực hiện việc đăng ký
 */

public class RegisterUseCase extends UseCase<RegisterUseCase.RequestValues, RegisterUseCase.ResponseValues> {

    public static final String INPUT_INVALID_USER_IS_NULL = "Đầu vào không hợp lệ.";
    public static final String USER_NAME_UNABLE_EMPTY = "Tên người dùng không thể để trống.";
    public static final String PASSWORD_UNABLE_EMPTY = "Mật khẩu không thể để trống.";
    public static final String USER_IS_EXISTING = "Người dùng đã tồn tại.";
    public static final String SAVE_USER_FAILED_INTERNAL_DB_ERROR = "Đăng ký thất bại. Lỗi cơ sở dữ liệu.";

    private Repository<Integer, User> userRepository;
    @Autowired
    public RegisterUseCase(Repository<Integer, User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        User user = requestValues.getUser();
        if(user == null){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_USER_IS_NULL));
        }
        else {
            String userName = user.getUserName();
            String password = user.getPassword();

            if(userName == null || userName.isEmpty()){
                callback.onCompleted(ComplexResponse.fail(USER_NAME_UNABLE_EMPTY));
            }
            else if(password == null || password.isEmpty()){
                callback.onCompleted(ComplexResponse.fail(PASSWORD_UNABLE_EMPTY));
            }
            else {
                List<User> users = userRepository.find(new FindUserByUserName(user.getUserName()));
                if(users != null && users.size() > 0){
                    callback.onCompleted(ComplexResponse.fail(USER_IS_EXISTING));
                }
                else{
                    boolean success = userRepository.save(user);
                    if(success){
                        callback.onCompleted(ComplexResponse.success());
                    }
                    else{
                        callback.onCompleted(ComplexResponse.fail(SAVE_USER_FAILED_INTERNAL_DB_ERROR));
                    }
                }
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private User user;

        public RequestValues(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
