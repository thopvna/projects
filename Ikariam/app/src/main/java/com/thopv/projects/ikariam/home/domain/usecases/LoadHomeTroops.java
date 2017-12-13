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

public class LoadHomeTroops extends UseCase<LoadHomeTroops.RequestValues, LoadHomeTroops.ResponseValues>{
    private Repository<HomeTroop, HomeTroop> homeTroopRepository;

    public LoadHomeTroops(Repository<HomeTroop, HomeTroop> homeTroopRepository) {
        this.homeTroopRepository = homeTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();

        HomeTroopByParty criteria = new HomeTroopByParty(party);
        List<HomeTroop> homeTroops = homeTroopRepository.find(criteria);
        if(homeTroops != null && homeTroops.size() > 0)
            callback.onCompleted(ComplexResponse.get(new ResponseValues(homeTroops)));
        else
            callback.onCompleted(ComplexResponse.fail("Không có một home troops nào cả."));
    }

    public static class RequestValues extends UseCase.RequestValues{
        private int party;

        public RequestValues(int party) {
            this.party = party;
        }

        public int getParty() {
            return party;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
        private List<HomeTroop> homeTroops;

        public List<HomeTroop> getHomeTroops() {
            return homeTroops;
        }

        public ResponseValues(List<HomeTroop> homeTroops) {
            this.homeTroops = homeTroops;
        }
    }
}
