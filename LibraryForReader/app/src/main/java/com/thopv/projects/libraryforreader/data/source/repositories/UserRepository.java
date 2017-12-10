package com.thopv.projects.libraryforreader.data.source.repositories;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.Specification;
import com.thopv.projects.libraryforreader.data.source.daos.UserDAO;
import com.thopv.projects.libraryforreader.data.source.mappers.UserMapper;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class UserRepository implements Repository<String, User> {
    public UserRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        userDAO = appDatabase.getUserDAO();
        userMapper = new UserMapper();
    }
    private UserMapper userMapper;
    private UserDAO userDAO;
    private AppDatabase appDatabase;
    
    @Override
    public boolean save(User user) {
        return userDAO.insert(userMapper.map(user)) > 0;
    }

    @Override
    public boolean delete(User user) {
        return userDAO.delete(userMapper.map(user)) > 0;
    }

    @Override
    public boolean clearAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> fetchAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> find(Specification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findById(String s) {
        return userDAO.fetchById(s);
    }

    @Override
    public void commitTransaction() {
        appDatabase.setTransactionSuccessful();
    }

    @Override
    public void runInTransacstion(Runnable runnable) {
        appDatabase.beginTransaction();
        runnable.run();
        appDatabase.endTransaction();
    }
}
