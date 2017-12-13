package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.home.domain.entity.Lo;
import com.thopv.projects.ikariam.home.domain.entity.Whale;

/**
 * Created by jlaotsezu on 25/11/2017.
 */

public class LoadEffectStatus extends UseCase<LoadEffectStatus.RequestValues, LoadEffectStatus.ResponseValues> {
    private Repository<Integer, Whale> whaleRepository;
    private Repository<Integer, Lo> loRepository;

    public LoadEffectStatus(Repository<Integer, Whale> whaleRepository, Repository<Integer, Lo> loRepository) {
        this.whaleRepository = whaleRepository;
        this.loRepository = loRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();
        Whale whale = whaleRepository.findById(party);
        Lo lo = loRepository.findById(party);
        String status;
        try {
            if (whale != null && lo != null) {
                status = "Whale(" + whale.getLevel() + "): " + DateUtils.getDistance(whale.getEndTime()) + ".\n"
                            + "Lo(" + lo.getLevel() + "): " + DateUtils.getDistance(lo.getEndTime()) + ".";
            } else if (whale != null) {
                status = "Whale(" + whale.getLevel() + "): " + DateUtils.getDistance(whale.getEndTime()) + ".";
            } else if (lo != null) {
                status = "Lo(" + lo.getLevel() + "): " + DateUtils.getDistance(lo.getEndTime()) + ".";
            } else {
                status = "K có hiệu ứng nào được kích hoạt.";
            }
            callback.onCompleted(ComplexResponse.success(new ResponseValues(status)));
        }
        catch (Exception ignored){
            callback.onCompleted(ComplexResponse.fail(ignored.getMessage()));
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
        private String status;

        public String getStatus() {
            return status;
        }

        public ResponseValues(String status) {
            this.status = status;
        }
    }
}
