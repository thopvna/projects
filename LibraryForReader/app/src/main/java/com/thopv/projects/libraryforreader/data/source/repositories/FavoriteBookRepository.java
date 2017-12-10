package com.thopv.projects.libraryforreader.data.source.repositories;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.Specification;
import com.thopv.projects.libraryforreader.data.source.daos.FavoriteBookDAO;
import com.thopv.projects.libraryforreader.data.source.mappers.FavoriteBookMapper;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class FavoriteBookRepository  implements Repository<Long, FavoriteBook>{
    private AppDatabase appDatabase;
    private FavoriteBookDAO favoriteBookDAO;
    private FavoriteBookMapper favoriteBookMapper;
    public FavoriteBookRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        favoriteBookDAO = appDatabase.getFavoriteBookDAO();
        favoriteBookMapper = new FavoriteBookMapper();
    }

    @Override
    public boolean save(FavoriteBook favoriteBook) {
        return favoriteBookDAO.insert(favoriteBookMapper.map(favoriteBook)) > 0;
    }

    @Override
    public boolean delete(FavoriteBook favoriteBook) {
        return favoriteBookDAO.delete(favoriteBookMapper.map(favoriteBook)) >  0;
    }

    @Override
    public boolean clearAll() {
        return favoriteBookDAO.clearAll() > 0;
    }

    @Override
    public List<FavoriteBook> fetchAll() {
        return favoriteBookDAO.fetchAll();
    }

    @Override
    public FavoriteBook findById(Long aLong) {
        return favoriteBookDAO.fetchById(aLong);
    }

    @Override
    public List<FavoriteBook> find(Specification specification) {
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
