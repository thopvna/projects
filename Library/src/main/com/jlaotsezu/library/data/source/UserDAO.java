package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;

import java.util.List;

public interface UserDAO {
    boolean save(User user);

    boolean update(User user);

    User fetchById(int userId);
    User fetchByUserName(String userName);

    void clearAll();

    List<User> findByKeyword(String keyword);
}
