package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.armies.SFieldTroop;
import com.thopv.projects.ikariam.data.source.daos.FieldTroopDAO;
import com.thopv.projects.ikariam.data.source.mappers.field_troops.FromEFieldTroopToSFieldTroop;
import com.thopv.projects.ikariam.data.source.mappers.field_troops.FromSFieldTroopListToEFieldTroopList;
import com.thopv.projects.ikariam.fight.domain.criteria.AllFieldTroopsAvailable;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackFieldTroop;
import com.thopv.projects.ikariam.fight.domain.criteria.AllFieldTroopsHasHitPoints;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.home.domain.criterias.AttackFieldTroopsAvailableCriteria;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class FieldTroopRepository implements Repository<FieldTroop, FieldTroop> {

    private AppDatabase appDatabase;
    private FieldTroopDAO fieldTroopDAO;
    private FromEFieldTroopToSFieldTroop fromEFieldTroopToSFieldTroopMapper;
    private FromSFieldTroopListToEFieldTroopList fromSFieldTroopListToEFieldTroopListMapper;
    public FieldTroopRepository(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
        fieldTroopDAO = appDatabase.getFieldTroopsDAO();
        fromSFieldTroopListToEFieldTroopListMapper = new FromSFieldTroopListToEFieldTroopList();
        fromEFieldTroopToSFieldTroopMapper = new FromEFieldTroopToSFieldTroop();
    }

    @Override
    public boolean insert(FieldTroop entity) {
        Assert.assertNotNull(entity);
        SFieldTroop sFieldTroop = fromEFieldTroopToSFieldTroopMapper.map(entity);
        return fieldTroopDAO.insert(sFieldTroop) > 0;
    }

    @Override
    public boolean insertAll(List<FieldTroop> entities) {
        Assert.assertNotNull(entities);
        Assert.assertTrue(entities.size() > 0);
        boolean success = true;
        for(FieldTroop fieldTroop : entities){
            boolean piece = insert(fieldTroop);
            if(!piece) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean delete(FieldTroop id) {
        Assert.assertNotNull(id);
        id.setAmount(0);
        SFieldTroop sFieldTroop = fromEFieldTroopToSFieldTroopMapper.map(id);
        return fieldTroopDAO.update(sFieldTroop) > 0;
    }

    /**
     * chỉ set amount = 0 chứ k phải delete hẳn
     * @param ids
     * @return
     */
    @Override
    public boolean deleteAll(List<FieldTroop> ids) {
        Assert.assertNotNull(ids);
        if(ids.size() <= 0)
            return true;
        boolean success = true;
        for(FieldTroop fieldTroop : ids){
            boolean piece = delete(fieldTroop);
            if(!piece)
                success = false;
        }
        return success;
    }

    @Override
    public boolean update(FieldTroop entity) {
        Assert.assertNotNull(entity);
        SFieldTroop sFieldTroop = fromEFieldTroopToSFieldTroopMapper.map(entity);
        return fieldTroopDAO.update(sFieldTroop) > 0;
    }

    @Override
    public boolean updateAll(List<FieldTroop> entities) {
        Assert.assertNotNull(entities);
        boolean success = true;
        for(FieldTroop fieldTroop : entities){
            boolean piece = update(fieldTroop);
            if(!piece)
                success = false;
        }
        return success;
    }

    @Override
    public List<FieldTroop> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FieldTroop findById(FieldTroop id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Q extends Criteria> List<FieldTroop> find(Q criteria) {
        if(criteria instanceof AttackFieldTroop){
            List<SFieldTroop> sFieldTroops = fieldTroopDAO.getAll(PartyUtils.getBlueParty());
            return fromSFieldTroopListToEFieldTroopListMapper.map(sFieldTroops);
        }
        else if(criteria instanceof AllFieldTroopsHasHitPoints){
            List<SFieldTroop> sFieldTroops = fieldTroopDAO.getAllHasHitPoints();
            return fromSFieldTroopListToEFieldTroopListMapper.map(sFieldTroops);
        }
        else if(criteria instanceof AllFieldTroopsAvailable){
            List<SFieldTroop> sFieldTroops = fieldTroopDAO.getAllAvailable();
            return fromSFieldTroopListToEFieldTroopListMapper.map(sFieldTroops);
        }
        else if(criteria instanceof AttackFieldTroopsAvailableCriteria){
            List<SFieldTroop> sFieldTroops = fieldTroopDAO.getAllAvailable(PartyUtils.getBlueParty());
            return fromSFieldTroopListToEFieldTroopListMapper.map(sFieldTroops);
        }
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
