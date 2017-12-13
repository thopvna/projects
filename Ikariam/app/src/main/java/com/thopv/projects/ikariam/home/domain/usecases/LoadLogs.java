package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class LoadLogs extends UseCase<LoadLogs.RequestValues, LoadLogs.ResponseValues>{
    private LogDAO logDAO;

    public LoadLogs(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int party = requestValues.getParty();
        List<ArmyLog> logs = logDAO.getAll();
        if(logs != null)
            getUseCaseCallback().onCompleted(ComplexResponse.get(new ResponseValues(logs)));
        else
            getUseCaseCallback().onCompleted(ComplexResponse.fail("Không có log nào liên quan tới House này."));
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
        private List<ArmyLog> logs;

        public List<ArmyLog> getLogs() {
            return logs;
        }

        public ResponseValues(List<ArmyLog> logs) {
            this.logs = logs;
        }
    }
}
