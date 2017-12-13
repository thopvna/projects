package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.Repository;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class LoadHouses extends UseCase<LoadHouses.RequestValues, LoadHouses.ResponseValues>{
    private Repository<Integer, House> houseRepository;

    public LoadHouses(Repository<Integer, House> houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<House> houses = houseRepository.loadAll();
        if(houses != null)
            callback.onCompleted(ComplexResponse.get(new ResponseValues(houses)));
        else
            callback.onCompleted(ComplexResponse.fail("Không có bất kỳ houses nào."));
    }

    public static class RequestValues extends UseCase.RequestValues{

    }
    public static class ResponseValues extends UseCase.ResponseValues{
        private List<House> houses;

        public List<House> getHouses() {
            return houses;
        }

        public ResponseValues(List<House> houses) {
            this.houses = houses;
        }
    }
}
