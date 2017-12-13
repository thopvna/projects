package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.home.domain.criterias.HomeTroopByParty;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;
import com.thopv.projects.ikariam.home.domain.entity.Whale;

import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/20/2017.
 */

public class SendUnits extends UseCase<SendUnits.RequestValues, SendUnits.ResponseValues> {
    private Repository<MovingTroop, MovingTroop> movingTroopRepository;
    private Repository<HomeTroop, HomeTroop> homeTroopRepository;
    private Repository<Integer, Whale> whaleRepository;
    private LogDAO logDAO;
    public SendUnits(Repository<MovingTroop, MovingTroop> movingTroopRepository, Repository<HomeTroop, HomeTroop> homeTroopRepository, Repository<Integer, Whale> whaleRepository, LogDAO logDAO) {
        this.movingTroopRepository = movingTroopRepository;
        this.homeTroopRepository = homeTroopRepository;
        this.whaleRepository = whaleRepository;
        this.logDAO = logDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        //validate input
        long goTime = requestValues.getGoTime();
        Map<String, Integer> unitsAmount = requestValues.getUnitsAmount();

        Assert.assertNotNull(unitsAmount);
        Assert.assertFalse(unitsAmount.size() == 0);

        List<HomeTroop> homeTroops = homeTroopRepository.find(new HomeTroopByParty(PartyUtils.getBlueParty()));
        List<MovingTroop> movingTroops = new LinkedList<>();
        long currentTimeInMillis = System.currentTimeMillis();

        for(HomeTroop homeTroop : homeTroops){
            String unitName = homeTroop.getUnitName();
            if(unitsAmount.containsKey(unitName)) {
                int subtracted = homeTroop.subtract(unitsAmount.get(unitName));
                MovingTroop movingTroop = getMovingTroop(goTime, currentTimeInMillis, unitName, subtracted);
                movingTroops.add(movingTroop);
            }
        }

        homeTroopRepository.beginTransaction();

        boolean updateOk = homeTroopRepository.updateAll(homeTroops);
        boolean insertOk = movingTroopRepository.insertAll(movingTroops);
        boolean logOk = isLogOk(movingTroops.get(0));

        boolean success = updateOk && insertOk && logOk;

        if(success) {
            homeTroopRepository.commitTransaction();
        }
        homeTroopRepository.endTransaction();
        callback.onCompleted(ComplexResponse.get(success));
    }

    private MovingTroop getMovingTroop(long goTime, long currentTimeInMillis, String unitName, int subtracted) {
        int blueParty = PartyUtils.getBlueParty();
        MovingTroop movingTroop = new MovingTroop.Builder()
                .setStartTime(currentTimeInMillis)
                .setEndTime(currentTimeInMillis + goTime)
                .setAmount(subtracted)
                .setUnitName(unitName)
                .build();
        Whale whale = whaleRepository.findById(blueParty);
        if(whale != null){
            movingTroop.setEndTime(whale.apply(movingTroop.getStartTime(), movingTroop.getEndTime()));
        }
        return movingTroop;
    }

    private boolean isLogOk(MovingTroop movingTroop) {
        ArmyLog armyLog = new ArmyLog.Builder()
                .setStartTime(movingTroop.getStartTime())
                .setType(ArmyLogType.ATTACK)
                .setContent("Bạn vừa gửi lính đến Defense House")
                .build();
        return logDAO.insert(armyLog) > 0;
    }

    public static class RequestValues extends UseCase.RequestValues{
        private Map<String, Integer> unitsAmount;
        private long goTime;

        public Map<String, Integer> getUnitsAmount() {
            return unitsAmount;
        }

        public RequestValues(Map<String, Integer> unitsAmount, long goTime) {
            this.unitsAmount = unitsAmount;
            this.goTime = goTime;
        }

        public long getGoTime() {
            return goTime;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
