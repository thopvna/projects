package com.thopv.projects.libraryforreader.data.source.repositories;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.Specification;
import com.thopv.projects.libraryforreader.data.source.daos.CartBookDAO;
import com.thopv.projects.libraryforreader.data.source.mappers.CartBookMapper;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class CartBookRepository implements Repository<Long, CartBook>{
    private AppDatabase appDatabase;
    private CartBookDAO cartBookDAO;
    private CartBookMapper cartBookMapper;
    public CartBookRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        cartBookDAO = appDatabase.getCartBookDAO();
        cartBookMapper = new CartBookMapper();
    }

    @Override
    public boolean save(CartBook cartBook) {
        return cartBookDAO.insert(cartBookMapper.map(cartBook)) > 0;
    }

    @Override
    public boolean delete(CartBook cartBook) {
        return cartBookDAO.delete(cartBookMapper.map(cartBook)) > 0;
    }

    @Override
    public boolean clearAll() {
        return cartBookDAO.clearAll() > 0;
    }

    @Override
    public List<CartBook> fetchAll() {
        return cartBookDAO.fetchAll();
    }

    @Override
    public CartBook findById(Long aLong) {
        return cartBookDAO.fetchById(aLong);
    }
    @Override
    public List<CartBook> find(Specification specification) {
        throw new UnsupportedOperationException();
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
