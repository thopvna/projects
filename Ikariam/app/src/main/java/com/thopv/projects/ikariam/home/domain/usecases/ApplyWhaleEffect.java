package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.home.domain.criterias.MovingTroopsNonConfirmAvailable;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;
import com.thopv.projects.ikariam.home.domain.entity.Whale;

import java.util.List;

/**
 * Created by jlaotsezu on 25/11/2017.
 */

public class ApplyWhaleEffect extends UseCase<ApplyWhaleEffect.RequestValues, ApplyWhaleEffect.ResponseValues> {
    private Repository<Integer, House> houseRepository;
    private Repository<Integer, Whale> whaleRepository;
    private Repository<MovingTroop, MovingTroop> movingTroopRepository;

    public ApplyWhaleEffect(Repository<Integer, House> houseRepository, Repository<Integer, Whale> whaleRepository, Repository<MovingTroop, MovingTroop> movingTroopMovingTroopRepository) {
        this.houseRepository = houseRepository;
        this.whaleRepository = whaleRepository;
        this.movingTroopRepository = movingTroopMovingTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int blueParty = PartyUtils.getBlueParty();
        House blueHouse = houseRepository.findById(blueParty);
        if(blueHouse != null){
            Whale whale = Whale.create(blueHouse);
            Whale oldWhale = whaleRepository.findById(blueParty);
            if(oldWhale == null) {
                boolean success = whaleRepository.insert(whale);
                boolean movingTroopsOk = applyMovingTroops(whale);
                callback.onCompleted(ComplexResponse.get(success && movingTroopsOk));
            }
            else{
                if(oldWhale.getNextTimeAvailable() > System.currentTimeMillis()){
                    try {
                        callback.onCompleted(ComplexResponse.fail("Unable apply whale effect. Next time available: " + DateUtils.getDistance(oldWhale.getNextTimeAvailable())));
                    }
                    catch (Exception ignored) {
                        callback.onCompleted(ComplexResponse.fail("Please try again."));
                    }
                }
                else{
                   boolean success =  whaleRepository.update(whale);
                   boolean movingTroopsOk = applyMovingTroops(whale);
                   callback.onCompleted(ComplexResponse.get(success && movingTroopsOk));
                }
            }
        }
        else{
            callback.onCompleted(ComplexResponse.fail("Don't have any house matching."));
        }
    }

    private boolean applyMovingTroops(Whale whale) {
        List<MovingTroop> movingTroops = movingTroopRepository.find(new MovingTroopsNonConfirmAvailable());
        if(movingTroops != null && movingTroops.size() > 0){
            for(MovingTroop movingTroop : movingTroops){
                movingTroop.setEndTime(whale.apply(movingTroop.getStartTime(), movingTroop.getEndTime()));
            }
            return movingTroopRepository.updateAll(movingTroops);
        }
        return true;
    }

    public static class RequestValues extends UseCase.RequestValues {

        public RequestValues() {
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
