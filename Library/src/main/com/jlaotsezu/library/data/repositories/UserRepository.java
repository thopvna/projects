package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByKeyword;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByUserName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class UserRepository implements Repository<Integer, User> {
    @Autowired
    UserDAO userDAO;
    @Override
    public boolean save(User user) {
        return userDAO.save(user);
    }

    @Override
    public boolean saveAll(List<User> users) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(User user) {
        return userDAO.update(user);
    }

    @Override
    public boolean updateAll(List<User> users) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAllById(List<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User fetchById(Integer integer) {
        return userDAO.fetchById(integer);
    }

    @Override
    public List<User> fetchAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> find(Specification specification) {
        if(specification instanceof FindUserByUserName){
            User user = userDAO.fetchByUserName(((FindUserByUserName) specification).getUserName());
            if(user == null) return null;
            return Collections.singletonList(user);
        }
        else if(specification instanceof FindUserByKeyword){
            return userDAO.findByKeyword(((FindUserByKeyword) specification).getKeyword());
        }
        throw new UnsupportedOperationException();
    }
}
