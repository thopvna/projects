package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.daos.HouseDAO;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class HouseRepository implements Repository<Integer, House> {

    private AppDatabase appDatabase;
    private HouseDAO houseDAO;
    public HouseRepository(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
        houseDAO = appDatabase.getHouseDAO();
    }

    @Override
    public boolean insert(House entity) {
        return houseDAO.add(entity) > 0;
    }

    @Override
    public boolean insertAll(List<House> entities) {
        return houseDAO.addAll(entities).length == entities.size();
    }

    @Override
    public boolean delete(Integer id) {
        return houseDAO.delete(id) > 0;
    }

    @Override
    public boolean deleteAll(List<Integer> ids) {
        List<House> houses = new LinkedList<>();
        for(int id : ids){
            House house = new House();
            house.setParty(id);
            houses.add(house);
        }
        return houseDAO.deleteAll(houses) == ids.size();
    }

    @Override
    public boolean update(House entity) {
        return houseDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<House> entities) {
        return houseDAO.updateAll(entities) == entities.size();
    }

    @Override
    public List<House> loadAll() {
        return houseDAO.getAlls();
    }

    @Override
    public House findById(Integer id) {
        return houseDAO.get(id);
    }

    @Override
    public <Q extends Criteria> List<House> find(Q criteria) {
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
