package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by thopv on 11/20/2017.
 */

public class GetHouse extends UseCase<GetHouse.RequestValues, GetHouse.ResponseValues>{
    private Repository<Integer, House> houseRepository;

    public GetHouse(Repository<Integer, House> houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();
        House house = houseRepository.findById(party);
        if(house != null)
            callback.onCompleted(ComplexResponse.get(new ResponseValues(house)));
        else
            callback.onCompleted(ComplexResponse.fail("Don't have any house matching."));
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
        private House house;

        public ResponseValues(House house) {
            this.house = house;
        }

        public House getHouse() {
            return house;
        }
    }
}
