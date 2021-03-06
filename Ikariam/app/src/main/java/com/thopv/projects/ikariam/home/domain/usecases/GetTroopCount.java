package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.home.domain.criterias.HomeTroopByParty;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class GetTroopCount extends UseCase<GetTroopCount.RequestValues, GetTroopCount.ResponseValues>{
    private Repository<HomeTroop, HomeTroop> homeTroopRepository;

    public GetTroopCount(Repository<HomeTroop, HomeTroop> homeTroopRepository) {
        this.homeTroopRepository = homeTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();
        HomeTroopByParty criteria = new HomeTroopByParty(party);
        List<HomeTroop> homeTroops = homeTroopRepository.find(criteria);
        int count = 0;
        if(homeTroops != null)
            for(HomeTroop homeTroop : homeTroops){
                count += homeTroop.getAmount();
            }
        callback.onCompleted(ComplexResponse.get(new ResponseValues(count)));
    }
    public static class RequestValues extends UseCase.RequestValues{
        private int party;

        public int getParty() {
            return party;
        }

        public RequestValues(int party) {
            this.party = party;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
        int count;

        public int getCount() {
            return count;
        }

        public ResponseValues(int count) {
            this.count = count;
        }
    }
}
