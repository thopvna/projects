package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.home.domain.entity.Lo;

import junit.framework.Assert;

/**
 * Created by jlaotsezu on 25/11/2017.
 */

public class ApplyLoEffect extends UseCase<ApplyLoEffect.RequestValues, ApplyLoEffect.ResponseValues> {
    private Repository<Integer, House> houseRepository;
    private Repository<Integer, Lo> loRepository;

    public ApplyLoEffect(Repository<Integer, House> houseRepository, Repository<Integer, Lo> loRepository) {
        this.houseRepository = houseRepository;
        this.loRepository = loRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();
        Assert.assertNotNull(party);
        House house = houseRepository.findById(party);
        if(house != null){
            Lo lo = Lo.create(house);
            Lo oldLo = loRepository.findById(party);
            if(oldLo == null) {
                boolean success = loRepository.insert(lo);
                callback.onCompleted(ComplexResponse.get(success));
            }
            else{
                if(oldLo.getNextTimeAvailable() > System.currentTimeMillis()){
                    try {
                        callback.onCompleted(ComplexResponse.fail("Unable apply lo effect. Next time available: " + DateUtils.getDistance(oldLo.getNextTimeAvailable())));
                    } 
                    catch (Exception ignored) {
                        callback.onCompleted(ComplexResponse.fail("Please try again."));
                    }
                }
                else{
                   boolean success =  loRepository.update(lo);
                   callback.onCompleted(ComplexResponse.get(success));
                }
            }
        }
        else{
            callback.onCompleted(ComplexResponse.fail("Don't have any house matching."));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int party;

        public int getParty() {
            return party;
        }

        public RequestValues(int party) {
            this.party = party;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
