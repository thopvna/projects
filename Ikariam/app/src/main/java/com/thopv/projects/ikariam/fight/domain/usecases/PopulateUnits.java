package com.thopv.projects.ikariam.fight.domain.usecases;

import android.util.Log;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;
import com.thopv.projects.ikariam.data.source.daos.FightStatusDAO;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.UnablePopulableTroopRepository;
import com.thopv.projects.ikariam.fight.domain.criteria.AllFieldTroopsAvailable;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackPopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackUnablePopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.criteria.HomePopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStatus;
import com.thopv.projects.ikariam.fight.domain.entity.fight.resources.FieldUtils;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulableTroop;
import com.thopv.projects.ikariam.fight.domain.entity.populate.BaseFieldPopulater;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulaterFactory;
import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class PopulateUnits extends UseCase<PopulateUnits.RequestValues, PopulateUnits.ResponseValues>{
    private Repository<PopulableTroop, PopulableTroop> populableTroopRepository;
    private Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository;
    private Repository<FieldTroop, FieldTroop> fieldTroopRepository;
    private Repository<Integer, House> houseRepository;
    private Repository<MovingTroop, MovingTroop> movingTroopRepository;
    private LogDAO logDAO;
    private FightStatusDAO fightStatusDAO;
    public PopulateUnits(Repository<PopulableTroop, PopulableTroop> populableTroopRepository, Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository, Repository<FieldTroop, FieldTroop> fieldTroopRepository, Repository<Integer, House> houseRepository, Repository<MovingTroop, MovingTroop> movingTroopRepository, LogDAO logDAO, FightStatusDAO fightStatusDAO) {
        this.populableTroopRepository = populableTroopRepository;
        this.unablePopulateTroopRepository = unablePopulateTroopRepository;
        this.fieldTroopRepository = fieldTroopRepository;
        this.houseRepository = houseRepository;
        this.movingTroopRepository = movingTroopRepository;
        this.logDAO = logDAO;
        this.fightStatusDAO = fightStatusDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        House house = houseRepository.findById(PartyUtils.getRedParty());
        if(house == null){
            callback.onCompleted(ComplexResponse.fail("Don't have any house matching."));
            return;
        }
        BaseFieldPopulater populater = PopulaterFactory.create(house.getPort().getLevel(), house.getShipyard().getLevel());

        populableTroopRepository.beginTransaction();

        List<PopulableTroop> attackPopulableTroops = populableTroopRepository.find(new AttackPopulableTroopCriteria());
        List<PopulableTroop> homePopulableTroops = populableTroopRepository.find(new HomePopulableTroopCriteria());
        List<FieldTroop> fieldTroops = fieldTroopRepository.find(new AllFieldTroopsAvailable());

        if(attackPopulableTroops == null)
            attackPopulableTroops = new LinkedList<>();
        if(homePopulableTroops == null)
            homePopulableTroops = new LinkedList<>();
        if(fieldTroops == null)
            fieldTroops = new LinkedList<>();

        boolean hasMainAttack = isHasMainAttack(fieldTroops);
        boolean hasMainDef = isHasMainDef(fieldTroops);

        boolean hasAttackPopulableTroops = attackPopulableTroops.size() > 0;
        boolean hasHomePopulableTroops = homePopulableTroops.size() > 0;

        if((hasAttackPopulableTroops && hasHomePopulableTroops) || (hasHomePopulableTroops && hasMainAttack) || (hasAttackPopulableTroops && hasMainDef)){
            populater.populate(fieldTroops, homePopulableTroops, attackPopulableTroops, (newInFieldTroops, newHomePopulableTroops, newAttackPopulableTroops) -> {
                boolean saveOk = saveState(newInFieldTroops, newHomePopulableTroops, newAttackPopulableTroops);
                if(saveOk) {
                    populableTroopRepository.commitTransaction();
                }
                populableTroopRepository.endTransaction();
                callback.onCompleted(ComplexResponse.get(saveOk));

            }, () -> {
                populableTroopRepository.endTransaction();
                callback.onCompleted(ComplexResponse.fail("All spaces was filled."));
            });
        }
        else if(!hasAttackPopulableTroops && !hasHomePopulableTroops && !hasMainAttack && !hasMainDef){
            if(clearBottomHomeFieldTroops(fieldTroops) &&
                clearBottomAttackFieldTroops(fieldTroops) && clearUnablePopulateTroop(PartyUtils.getBlueParty()) && clearUnablePopulateTroop(PartyUtils.getRedParty()))
                populableTroopRepository.commitTransaction();
            populableTroopRepository.endTransaction();
            callback.onCompleted(ComplexResponse.fail("Field is empty..."));
        }
        else {
            FightStatus fightStatus = fightStatusDAO.fetch();
            if(fightStatus != null) {
                fightStatus.setFighting(false);
                fightStatusDAO.update(fightStatus);
            }
            if(!hasAttackPopulableTroops && !hasMainAttack){
                boolean success = bringFieldToHomePopulableTroops(fieldTroops, homePopulableTroops)
                        && clearBottomAttackFieldTroops(fieldTroops);
                if(success) {
                    populableTroopRepository.commitTransaction();
                }
            }
            if(!hasHomePopulableTroops && !hasMainDef){
                boolean success = returnUnits(fieldTroops, attackPopulableTroops)
                        && clearBottomHomeFieldTroops(fieldTroops);
                if(success) {
                    populableTroopRepository.commitTransaction();
                }
            }
            populableTroopRepository.endTransaction();
            callback.onCompleted(ComplexResponse.fail("Unable populable - attack or def troops is null."));
        }
    }

    private boolean saveState(List<FieldTroop> fieldTroops,List<PopulableTroop> homePopulableTroops, List<PopulableTroop> attackPopulableTroops ){
        boolean hasMainAttack = isHasMainAttack(fieldTroops);
        boolean hasMainDef = isHasMainDef(fieldTroops);

        boolean homePopulableOk = updateHomePopulableTroops(fieldTroops, homePopulableTroops, hasMainDef);
        boolean attackPopulableOk = updateAttackPopulableTroops(fieldTroops, attackPopulableTroops, hasMainAttack);
        boolean fieldOk = fieldTroopRepository.insertAll(fieldTroops);

        return homePopulableOk && attackPopulableOk && fieldOk;
    }

    private boolean isHasMainDef(List<FieldTroop> newInFieldTroops) {
        List<Integer> redMainIds = FieldUtils.getMainRedViewIds();
        for(FieldTroop fieldTroop : newInFieldTroops){
            if(fieldTroop.isAlive() && redMainIds.contains(fieldTroop.getViewId())){
                return true;
            }
        }
        return false;
    }

    private boolean isHasMainAttack(List<FieldTroop> newInFieldTroops) {
        List<Integer> blueMainIds = FieldUtils.getMainBlueViewIds();
        for(FieldTroop fieldTroop : newInFieldTroops){
            if(fieldTroop.isAlive() && blueMainIds.contains(fieldTroop.getViewId())){
                Log.e(getClass().getSimpleName(), "Have a view id: " + Integer.toHexString(fieldTroop.getViewId()));
                return true;
            }
        }
        return false;
    }

    private boolean updateAttackPopulableTroops(List<FieldTroop> newInFieldTroops, List<PopulableTroop> newAttackPopulableTroops, boolean hasMainAttack) {
        boolean attackPopulableOk;
        boolean fieldOk = true;
        if(hasMainAttack) {
            attackPopulableOk = populableTroopRepository.updateAll(newAttackPopulableTroops);
        }
        else {
            attackPopulableOk = populableTroopRepository.deleteAll(newAttackPopulableTroops);
            fieldOk = clearBottomAttackFieldTroops(newInFieldTroops);
        }
        return attackPopulableOk && fieldOk;
    }

    private boolean clearBottomAttackFieldTroops(List<FieldTroop> newInFieldTroops) {
        List<Integer> bottomBlueTroopsViewIds = FieldUtils.getBlueBottomTroopsViewIds();
        for(FieldTroop fieldTroop : newInFieldTroops){
            if(bottomBlueTroopsViewIds.contains(fieldTroop.getViewId()))
                fieldTroop.clear();
        }
        return fieldTroopRepository.updateAll(newInFieldTroops);
    }

    private boolean updateHomePopulableTroops(List<FieldTroop> newInFieldTroops, List<PopulableTroop> newHomePopulableTroops, boolean hasMainDef) {
        boolean homePopulableOk;
        boolean fieldOk = true;
        if(hasMainDef)
            homePopulableOk = populableTroopRepository.updateAll(newHomePopulableTroops);
        else {
            homePopulableOk = populableTroopRepository.deleteAll(newHomePopulableTroops);
            fieldOk = clearBottomHomeFieldTroops(newInFieldTroops);
        }
        return homePopulableOk && fieldOk;
    }

    private boolean clearBottomHomeFieldTroops(List<FieldTroop> newInFieldTroops) {
        List<Integer> bottomRedTroopsViewIds = FieldUtils.getRedBottomTroopsViewIds();
        for(FieldTroop fieldTroop : newInFieldTroops){
            if(bottomRedTroopsViewIds.contains(fieldTroop.getViewId()))
                fieldTroop.clear();
        }
        return fieldTroopRepository.updateAll(newInFieldTroops);
    }
    private boolean clearUnablePopulateTroop(int party){
        List<UnablePopulateTroop> unablePopulateTroops = unablePopulateTroopRepository.loadAll();
        for(UnablePopulateTroop unablePopulateTroop : unablePopulateTroops){
            if(unablePopulateTroop.getParty() == party){
                unablePopulateTroop.clear();
            }
        }
        return unablePopulateTroopRepository.updateAll(unablePopulateTroops);
    }
    private boolean bringFieldToHomePopulableTroops(List<FieldTroop> fieldTroops, List<PopulableTroop> populableTroops){
        List<UnablePopulateTroop> unablePopulateTroops = unablePopulateTroopRepository.loadAll();
        if(unablePopulateTroops == null) unablePopulateTroops = new LinkedList<>();
        for(UnablePopulateTroop unablePopulateTroop : unablePopulateTroops){
            if(unablePopulateTroop.getParty() == PartyUtils.getRedParty()){
                addUnitToPopulableTroops(populableTroops,
                        unablePopulateTroop.getUnitName(),
                        unablePopulateTroop.subtract(unablePopulateTroop.getAmount()),
                        unablePopulateTroop.getParty());
            }
            else{
                unablePopulateTroop.clear();
            }
        }
        for(FieldTroop fieldTroop : fieldTroops){
            if(fieldTroop.isAlive() && fieldTroop.getParty() == PartyUtils.getRedParty()){
                addUnitToPopulableTroops(populableTroops,
                        fieldTroop.getUnitName(),
                        fieldTroop.subtractUnits(fieldTroop.getAmount()),
                        fieldTroop.getParty());
            }
        }
        return fieldTroopRepository.updateAll(fieldTroops) &&  populableTroopRepository.insertAll(populableTroops) && unablePopulateTroopRepository.updateAll(unablePopulateTroops);
    }

    private void addUnitToPopulableTroops(List<PopulableTroop> populableTroops, String unitName, int amount, int party) {
        PopulableTroop populableTroop = new PopulableTroop(unitName, amount, party);
        if(populableTroops.contains(populableTroop)){
            PopulableTroop oldPopulableTroop = populableTroops.get(populableTroops.indexOf(populableTroop));
            oldPopulableTroop.merge(populableTroop);
        }
        else {
            populableTroops.add(populableTroop);
        }
    }

    private boolean returnUnits(List<FieldTroop> fieldTroops, List<PopulableTroop> attackpopulableTroops){
        //TODO: Chỗ này tạm fix cứng.
        long goTime = 5000L;
        long timeInMillis = System.currentTimeMillis();
        ArmyLog armyLog = new ArmyLog.Builder()
                .setType(ArmyLogType.RETURN)
                .setStartTime(timeInMillis)
                .setContent("Chiến đầu kết thúc. Quân của bạn đã rút về.")
                .build();
        List<UnablePopulateTroop> unablePopulateTroops = unablePopulateTroopRepository.loadAll();
        if(unablePopulateTroops == null) unablePopulateTroops = new LinkedList<>();
        List<MovingTroop> movingTroops = new LinkedList<>();

        for(FieldTroop fieldTroop : fieldTroops){
            if(fieldTroop.isAlive() && fieldTroop.getParty() == PartyUtils.getBlueParty()){
                MovingTroop movingTroop = getMovingTroop(fieldTroop.getUnitName(), fieldTroop.subtractUnits(fieldTroop.getAmount()), timeInMillis, goTime);
                addMovingTroopToList(movingTroop, movingTroops);
            }
            else{
                fieldTroop.clear();
            }
        }
        for(PopulableTroop attackTroop : attackpopulableTroops){
            MovingTroop movingTroop = getMovingTroop(attackTroop.getUnitName(), attackTroop.subtractAmount(attackTroop.getAmount()), timeInMillis, goTime);
            addMovingTroopToList(movingTroop, movingTroops);
        }
        for(UnablePopulateTroop unablePopulateTroop : unablePopulateTroops){
            if(unablePopulateTroop.getParty() == PartyUtils.getBlueParty()){
                MovingTroop movingTroop = getMovingTroop(unablePopulateTroop.getUnitName(), unablePopulateTroop.subtract(unablePopulateTroop.getAmount()), timeInMillis, goTime);
                addMovingTroopToList(movingTroop, movingTroops);
            }
            else{
                unablePopulateTroop.clear();
            }
        }

        return fieldTroopRepository.updateAll(fieldTroops) &&  populableTroopRepository.insertAll(attackpopulableTroops) && movingTroopRepository.insertAll(movingTroops) && logDAO.insert(armyLog) > 0 && unablePopulateTroopRepository.updateAll(unablePopulateTroops);
    }
    private MovingTroop getMovingTroop(String unitName, int amount, long currentTimeInMillis, long goTime) {
        long endTime = currentTimeInMillis + goTime;
        return new MovingTroop.Builder()
                .setAmount(amount)
                .setUnitName(unitName)
                .setStartTime(currentTimeInMillis)
                .setEndTime(endTime)
                .setReturn(true)
                .build();
    }
    private void addMovingTroopToList(MovingTroop movingTroop, List<MovingTroop> movingTroops){
        if(!movingTroops.contains(movingTroop)){
            movingTroops.add(movingTroop);
        }
        else{
            MovingTroop old = movingTroops.get(movingTroops.indexOf(movingTroop));
            old.merge(movingTroop);
        }
    }
    public static class RequestValues extends UseCase.RequestValues{

    }
    public static class ResponseValues extends UseCase.ResponseValues{
    }
}
