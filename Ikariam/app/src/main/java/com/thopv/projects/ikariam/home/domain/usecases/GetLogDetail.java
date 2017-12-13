package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.CollosusedTroop;
import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogDetail;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.home.domain.criterias.CollosusedTroopsStartAt;
import com.thopv.projects.ikariam.home.domain.criterias.MovingTroopsStartAt;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class GetLogDetail extends UseCase<GetLogDetail.RequestValues, GetLogDetail.ResponseValues> {
    private Repository<MovingTroop, MovingTroop> movingTroopRepository;
    private Repository<CollosusedTroop, CollosusedTroop> collosusedTroopRepository;

    public GetLogDetail(Repository<MovingTroop, MovingTroop> movingTroopRepository, Repository<CollosusedTroop, CollosusedTroop> collosusedTroopRepository) {
        this.movingTroopRepository = movingTroopRepository;
        this.collosusedTroopRepository = collosusedTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        ArmyLog armyLog = requestValues.getArmyLog();
        Assert.assertNotNull(armyLog);

        ArmyLogDetail.Builder builder = new ArmyLogDetail.Builder(armyLog);
        List<BaseTroop> baseTroops = new LinkedList<>();

        if(armyLog.getType().equalsIgnoreCase( ArmyLogType.ATTACK) || armyLog.getType().equalsIgnoreCase(ArmyLogType.RETURN)){
            List<MovingTroop> movingTroops = movingTroopRepository.find(new MovingTroopsStartAt(armyLog.getStartTime()));
            for(MovingTroop movingTroop : movingTroops){
                baseTroops.add(new BaseTroop(movingTroop.getUnitName(), movingTroop.getAmount()));
                builder.setEndTime(movingTroop.getEndTime());
            }
        }
        else if(armyLog.getType().equalsIgnoreCase( ArmyLogType.COLLOSUS)){
            List<CollosusedTroop> collosusedTroops = collosusedTroopRepository.find(new CollosusedTroopsStartAt(armyLog.getStartTime()));
            for(CollosusedTroop collosusedTroop : collosusedTroops){
                baseTroops.add(new BaseTroop(collosusedTroop.getUnitName(), collosusedTroop.getAmount()));
                builder.setEndTime(collosusedTroop.getEndTime());
            }
        }
        builder.setTroops(baseTroops);

        if(baseTroops.size() > 0){
            callback.onCompleted(ComplexResponse.get(new ResponseValues(builder.build())));
        }
        else{
            callback.onCompleted(ComplexResponse.fail("Don't have any troops matching."));
        }
    }

    public static class RequestValues extends UseCase.RequestValues{
        private ArmyLog armyLog;

        public RequestValues(ArmyLog armyLog) {
            this.armyLog = armyLog;
        }

        public ArmyLog getArmyLog() {
            return armyLog;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
        private ArmyLogDetail armyLogDetail;

        public ResponseValues(ArmyLogDetail armyLogDetail) {
            this.armyLogDetail = armyLogDetail;
        }

        public ArmyLogDetail getArmyLogDetail() {
            return armyLogDetail;
        }
    }
}

