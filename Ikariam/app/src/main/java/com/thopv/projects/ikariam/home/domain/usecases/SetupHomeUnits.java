package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.source.repositories.Repository;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by thopv on 11/20/2017.
 */

public class SetupHomeUnits extends UseCase<SetupHomeUnits.RequestValues, SetupHomeUnits.ResponseValues>{
    private Repository<HomeTroop, HomeTroop> homeTroopRepository;

    public SetupHomeUnits(Repository<HomeTroop, HomeTroop> homeTroopRepository) {
        this.homeTroopRepository = homeTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<HomeTroop> homeTroops = requestValues.getHomeTroops();
        assertNotNull(homeTroops);
        assertFalse(homeTroops.size() == 0);

        boolean success = homeTroopRepository.insertAll(homeTroops);

        callback.onCompleted(ComplexResponse.get(success));
    }

    public static class RequestValues extends UseCase.RequestValues{
        private List<HomeTroop> homeTroops;

        public RequestValues(List<HomeTroop> homeTroops) {
            this.homeTroops = homeTroops;
        }

        public List<HomeTroop> getHomeTroops() {
            return homeTroops;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
