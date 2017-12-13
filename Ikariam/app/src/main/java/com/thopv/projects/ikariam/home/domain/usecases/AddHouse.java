package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.Repository;

import junit.framework.Assert;

/**
 * Created by thopv on 11/20/2017.
 */

public class AddHouse extends UseCase<AddHouse.RequestValues, AddHouse.ResponseValues>{
    private Repository<Integer, House> houseRepository;
    public AddHouse(Repository<Integer, House> houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        House house = requestValues.getHouse();
        Assert.assertNotNull(house);

        boolean success = houseRepository.insert(house);

        callback.onCompleted(ComplexResponse.get(success));
    }

    public static class RequestValues extends UseCase.RequestValues{
        private House house;

        public RequestValues(House house) {
            this.house = house;
        }

        public House getHouse() {
            return house;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
    }
}
