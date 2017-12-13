package com.thopv.projects.ikariam.fight.domain.usecases;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackUnablePopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.domain.criterias.AttackFieldTroopsAvailableCriteria;
import com.thopv.projects.ikariam.home.domain.criterias.AttackTroopsAvailableCriteria;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;
import com.thopv.projects.ikariam.home.domain.entity.Whale;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/21/2017.
 */

public class ReturnUnits extends UseCase<ReturnUnits.RequestValues, ReturnUnits.ResponseValues>{
    private Repository<FieldTroop, FieldTroop> fieldTroopRepository;
    private Repository<MovingTroop, MovingTroop> movingTroopRepository;
    private Repository<AttackTroop, AttackTroop> attackTroopRepository;
    private Repository<Integer, Whale> whaleRepository;
    private Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository;
    private LogDAO logDAO;

    public ReturnUnits(Repository<FieldTroop, FieldTroop> fieldTroopRepository, Repository<MovingTroop, MovingTroop> movingTroopRepository, Repository<AttackTroop, AttackTroop> attackTroopRepository, Repository<Integer, Whale> whaleRepository, Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository, LogDAO logDAO) {
        this.fieldTroopRepository = fieldTroopRepository;
        this.movingTroopRepository = movingTroopRepository;
        this.attackTroopRepository = attackTroopRepository;
        this.whaleRepository = whaleRepository;
        this.unablePopulateTroopRepository = unablePopulateTroopRepository;
        this.logDAO = logDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        //validate
        Map<String, Integer> unitsAmount = requestValues.getUnitsAmount();
        long goTime = requestValues.getGoTime();
        //TODO: Cho phép chọn đúng loại quân cần rút về.
        attackTroopRepository.beginTransaction();

        List<AttackTroop> attackTroops = attackTroopRepository.find(new AttackTroopsAvailableCriteria());
        List<FieldTroop> fieldTroops = fieldTroopRepository.find(new AttackFieldTroopsAvailableCriteria());
        List<UnablePopulateTroop> unablePopulateTroops = unablePopulateTroopRepository.find(new AttackUnablePopulableTroopCriteria());
        List<MovingTroop> movingTroops = new LinkedList<>();
        long timeInMillis = System.currentTimeMillis();

        boolean hasAttackTroops = attackTroops != null && attackTroops.size() != 0;
        boolean hasFieldTroops = fieldTroops != null && fieldTroops.size() != 0;
        boolean hasUnablePopulateTroops = unablePopulateTroops != null && unablePopulateTroops.size() != 0;

        if (!hasAttackTroops && !hasFieldTroops && !hasUnablePopulateTroops) {
            attackTroopRepository.endTransaction();
            callback.onCompleted(ComplexResponse.fail("Không có lính nào của nhà này ở đây."));
        } else {
            handleOnHasReturnableTroops(callback, unitsAmount, goTime, attackTroops, fieldTroops, movingTroops, unablePopulateTroops, timeInMillis, hasAttackTroops, hasFieldTroops, hasUnablePopulateTroops);
        }
    }

    private void handleOnHasReturnableTroops(UseCaseCallback<ResponseValues> callback, Map<String, Integer> unitsAmount, long goTime, List<AttackTroop> attackTroops, List<FieldTroop> fieldTroops, List<MovingTroop> movingTroops, List<UnablePopulateTroop> unablePopulateTroops,  long timeInMillis, boolean hasAttackTroops, boolean hasFieldTroops, boolean hasUnablePopulableTroops) {
        ArmyLog armyLog = buildLog(timeInMillis);
        //
        if (hasAttackTroops) {
            returnAttackTroops(unitsAmount, goTime, attackTroops, movingTroops, timeInMillis);
        }
        if (hasFieldTroops) {
            returnFieldTroops(unitsAmount, goTime, fieldTroops, movingTroops, timeInMillis);
        }
        if(hasUnablePopulableTroops){
            returnUnablePopulableTroops(unitsAmount, goTime, unablePopulateTroops, movingTroops, timeInMillis);
        }

        boolean fieldOk = true;
        boolean attackOk = true;
        boolean unablePopulableOk = true;
        boolean movingOk;
        boolean logOk;

        if (hasFieldTroops) {
            fieldOk = fieldTroopRepository.updateAll(fieldTroops);
        }
        if (hasAttackTroops) {
            attackOk = attackTroopRepository.updateAll(attackTroops);
        }
        if(hasUnablePopulableTroops){
            unablePopulableOk = unablePopulateTroopRepository.updateAll(unablePopulateTroops);
        }
        movingOk = movingTroopRepository.insertAll(movingTroops);

        logOk = armyLog == null || logDAO.insert(armyLog) > 0;

        boolean success = movingOk && logOk && attackOk && fieldOk && unablePopulableOk;
        if (success) {
            attackTroopRepository.commitTransaction();
        }

        attackTroopRepository.endTransaction();
        callback.onCompleted(ComplexResponse.get(success));
    }

    private void returnFieldTroops(Map<String, Integer> unitsAmount, long goTime, List<FieldTroop> fieldTroops, List<MovingTroop> movingTroops, long timeInMillis) {
        for (FieldTroop fieldTroop : fieldTroops) {
            if (unitsAmount.containsKey(fieldTroop.getUnitName())) {
                int expected = unitsAmount.get(fieldTroop.getUnitName());
                MovingTroop movingTroop = getMovingTroop(fieldTroop.getUnitName(), fieldTroop.subtractUnits(expected), timeInMillis, goTime);
                addMovingTroopToList(movingTroop, movingTroops);
            }
        }
    }

    private void returnAttackTroops(Map<String, Integer> unitsAmount, long goTime, List<AttackTroop> attackTroops, List<MovingTroop> movingTroops, long timeInMillis) {
        for (AttackTroop attackTroop : attackTroops) {
            if (unitsAmount.containsKey(attackTroop.getUnitName())) {
                int expected = unitsAmount.get(attackTroop.getUnitName());
                MovingTroop movingTroop = getMovingTroop(attackTroop.getUnitName(), attackTroop.subtractAmount(expected), timeInMillis, goTime);
                addMovingTroopToList(movingTroop, movingTroops);
            }
        }
    }
    private void returnUnablePopulableTroops(Map<String, Integer> unitsAmount, long goTime, List<UnablePopulateTroop> unablePopulateTroops, List<MovingTroop> movingTroops, long timeInMillis) {
        for (UnablePopulateTroop unablePopulateTroop : unablePopulateTroops) {
            if (unitsAmount.containsKey(unablePopulateTroop.getUnitName())) {
                MovingTroop movingTroop = getMovingTroop(unablePopulateTroop.getUnitName(), unablePopulateTroop.getAmount(), timeInMillis, goTime);
                unablePopulateTroop.clear();
                addMovingTroopToList(movingTroop, movingTroops);
            }
        }
    }

    private ArmyLog buildLog(long timeInMillis) {
        ArmyLog armyLog;
        armyLog = new ArmyLog.Builder()
                .setType(ArmyLogType.RETURN)
                .setStartTime(timeInMillis)
                .setContent("Bạn vừa rút quân.")
                .build();
        return armyLog;
    }

    private MovingTroop getMovingTroop(String unitName, int amount, long currentTimeInMillis, long goTime) {
        long endTime = currentTimeInMillis + goTime;
        Whale whale = whaleRepository.findById(PartyUtils.getBlueParty());
        if(whale != null){
            endTime = whale.apply(currentTimeInMillis, endTime);
        }
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
        private Map<String, Integer> unitsAmount;
        private long goTime;

        public RequestValues(Map<String, Integer> unitsAmount, long goTime) {
            this.unitsAmount = unitsAmount;
            this.goTime = goTime;
        }

        public Map<String, Integer> getUnitsAmount() {
            return unitsAmount;
        }

        public long getGoTime() {
            return goTime;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
