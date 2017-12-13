package com.thopv.projects.ikariam.fight.domain.usecases;

import android.util.Log;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.data.source.daos.FightStatusDAO;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.fight.domain.criteria.AllFieldTroopsHasHitPoints;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStatus;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStrategy;
import com.thopv.projects.ikariam.fight.domain.entity.fight.SmartFightStrategy;
import com.thopv.projects.ikariam.fight.domain.entity.fight.resources.FieldUtils;
import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.domain.entity.Lo;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/22/2017.
 */

public class Fight extends UseCase<Fight.RequestValues, Fight.ResponseValues>{
    private Repository<FieldTroop, FieldTroop> fieldTroopRepository;
    private Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository;
    private Repository<Integer, Lo> loRepository;
    private Repository<Integer, House> houseRepository;
    private FightStatusDAO fightStatusDAO;
    public Fight(Repository<FieldTroop, FieldTroop> fieldTroopRepository, Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository, Repository<Integer, Lo> loRepository, Repository<Integer, House> houseRepository, FightStatusDAO fightStatusDAO) {
        this.fieldTroopRepository = fieldTroopRepository;
        this.unablePopulateTroopRepository = unablePopulateTroopRepository;
        this.loRepository = loRepository;
        this.houseRepository = houseRepository;
        this.fightStatusDAO = fightStatusDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        House house = houseRepository.findById(PartyUtils.getRedParty());
        if(house == null) {
            return;
        }
        fieldTroopRepository.beginTransaction();

        List<FieldTroop> fieldTroops = fieldTroopRepository.find(new AllFieldTroopsHasHitPoints());
        Lo blueLo = loRepository.findById(PartyUtils.getBlueParty());
        Lo redLo = loRepository.findById(PartyUtils.getRedParty());

        applyLoEffect(fieldTroops, blueLo);
        applyLoEffect(fieldTroops, redLo);
        //TODO: Bắt cho được sự kiện start fight.
        boolean fightable = checkFightable(fieldTroops);
        //TODO: Tăng turn lên mỗi lần.
        if(fightable) {
            boolean blueFightFirst = logFightStatus() % 2 == 0;
            FightStrategy fightStrategy = new SmartFightStrategy(blueFightFirst, fieldTroops, newFieldTroops -> {
                List<UnablePopulateTroop> unablePopulateTroops =  checkUnableAttackTroops(newFieldTroops);
                boolean success = fieldTroopRepository.updateAll(newFieldTroops) && unablePopulateTroopRepository.insertAll(unablePopulateTroops);
                if (success)
                    fieldTroopRepository.commitTransaction();
                fieldTroopRepository.endTransaction();

                callback.onCompleted(ComplexResponse.get(success));
            });
            fightStrategy.fight();
        }
        else{
            fieldTroopRepository.endTransaction();
            callback.onCompleted(ComplexResponse.fail("Trận đầu không thể bắt đầu. Không có đủ lính(bên ta hoặc địch) trên bàn đấu."));
        }
    }

    private List<UnablePopulateTroop> checkUnableAttackTroops(List<FieldTroop> newFieldTroops) {
        List<UnablePopulateTroop> unablePopulateTroops = unablePopulateTroopRepository.loadAll();
        if(unablePopulateTroops == null) unablePopulateTroops =  new LinkedList<>();
        for(FieldTroop fieldTroop : newFieldTroops){
            if(fieldTroop.isAlive() && !fieldTroop.hasMunitions()){
                UnablePopulateTroop unablePopulateTroop = new UnablePopulateTroop(fieldTroop.getUnitName(), fieldTroop.subtractUnits(fieldTroop.getAmount()), fieldTroop.getParty());
                if(unablePopulateTroops.contains(unablePopulateTroop)){
                    UnablePopulateTroop old = unablePopulateTroops.get(unablePopulateTroops.indexOf(unablePopulateTroop));
                    old.merge(unablePopulateTroop);
                }
                else{
                    unablePopulateTroops.add(unablePopulateTroop);
                }
            }
        }
        return unablePopulateTroops;
    }

    /**
     * return turn count;
     * @return
     */
    private int logFightStatus() {
        FightStatus fightStatus = fightStatusDAO.fetch();
        if(fightStatus == null || !fightStatus.isFighting()){
            fightStatus = new FightStatus(1, System.currentTimeMillis(), true, 0);
        }
        fightStatus.newTurn();
        fightStatusDAO.insert(fightStatus);
        return fightStatus.getTurn();
    }

    private boolean checkFightable(List<FieldTroop> fieldTroops){
        List<Integer> blueTeamViewIds = FieldUtils.getBlueViewIds();
        List<Integer> redTeamViewIds = FieldUtils.getRedViewIds();
        boolean redOk = false;
        boolean blueOk = false;
        for(FieldTroop fieldTroop : fieldTroops){
            int viewId = fieldTroop.getViewId();
            if(!blueOk && blueTeamViewIds.contains(viewId)){
                blueOk = true;
            }
            if(!redOk && redTeamViewIds.contains(viewId)){
                redOk = true;
            }
            if(blueOk && redOk){
                return true;
            }
        }
        return false;
    }


    private void applyLoEffect(List<FieldTroop> fieldTroops, Lo lo) {
        if(lo != null){
            for(FieldTroop fieldTroop : fieldTroops){
                if(fieldTroop.getParty() == lo.getParty()) {
                    fieldTroop.setExtraDamePercent(lo.getExtraDamePercent());
                    fieldTroop.setExtraArmour(lo.getExtraArmour());
                }
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues{
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
