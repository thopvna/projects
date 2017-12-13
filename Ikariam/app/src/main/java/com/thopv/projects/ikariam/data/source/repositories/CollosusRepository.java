package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.CollosusDAO;
import com.thopv.projects.ikariam.home.domain.entity.Collosus;

import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */

public class CollosusRepository implements Repository<Integer, Collosus> {
    private AppDatabase appDatabase;
    private CollosusDAO collosusDAO;

    public CollosusRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        this.collosusDAO = appDatabase.getCollosusDAO();
    }

    @Override
    public boolean insert(Collosus entity) {
        return collosusDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<Collosus> entities) {
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
    public boolean update(Collosus entity) {
        return collosusDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<Collosus> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Collosus> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collosus findById(Integer id) {
        return collosusDAO.fetchById(id);
    }

    @Override
    public <Q extends Criteria> List<Collosus> find(Q criteria) {
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
