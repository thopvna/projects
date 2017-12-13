package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by thopv on 11/20/2017.
 */

public class RemoveHouse extends UseCase<RemoveHouse.RequestValues, RemoveHouse.ResponseValues>{
    private Repository<Integer, House> houseRepository;

    public RemoveHouse(Repository<Integer, House> houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();
        boolean success = houseRepository.delete(party);
        callback.onCompleted(ComplexResponse.get(success));
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

    }
}
