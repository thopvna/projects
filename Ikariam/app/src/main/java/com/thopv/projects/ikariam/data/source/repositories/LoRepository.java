package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.LoDAO;
import com.thopv.projects.ikariam.home.domain.entity.Lo;

import java.util.List;

/**
 * Created by jlaotsezu on 25/11/2017.
 */

public class LoRepository implements Repository<Integer, Lo> {
    private AppDatabase appDatabase;
    private LoDAO loDAO;
    public LoRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        this.loDAO = appDatabase.getLoDAO();
    }

    @Override
    public boolean insert(Lo entity) {
        return loDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<Lo> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(List<Integer> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Lo entity) {
        return loDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<Lo> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Lo> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Lo findById(Integer id) {
        return loDAO.fetchById(id);
    }

    @Override
    public <Q extends Criteria> List<Lo> find(Q criteria) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void runInTransaction(Runnable runnable) {
        appDatabase.runInTransaction(runnable);
    }
    @Override
    public void beginTransaction() {
        appDatabase.beginTransaction();
    }

    @Override
    public void endTransaction() {
        appDatabase.endTransaction();
    }

    @Override
    public void commitTransaction() {
        appDatabase.setTransactionSuccessful();
    }

}
