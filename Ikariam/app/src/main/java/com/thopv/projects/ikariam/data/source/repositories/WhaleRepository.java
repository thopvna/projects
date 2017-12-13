package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.WhaleDAO;
import com.thopv.projects.ikariam.home.domain.entity.Whale;

import java.util.List;

/**
 * Created by jlaotsezu on 25/11/2017.
 */

public class WhaleRepository implements Repository<Integer, Whale> {
    private AppDatabase appDatabase;
    private WhaleDAO whaleDAO;
    public WhaleRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        whaleDAO = appDatabase.getWhaleDAO();
    }

    @Override
    public boolean insert(Whale entity) {
        return whaleDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<Whale> entities) {
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
    public boolean update(Whale entity) {
        return whaleDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<Whale> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Whale> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Whale findById(Integer id) {
        return whaleDAO.fetchById(id);
    }

    @Override
    public <Q extends Criteria> List<Whale> find(Q criteria) {
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
