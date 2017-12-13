package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;

import junit.framework.Assert;

/**
 * Created by thopv on 11/20/2017.
 */

public class AddLog extends UseCase<AddLog.RequestValues, AddLog.ResponseValues> {
    private LogDAO logDAO;

    public AddLog(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        ArmyLog log = requestValues.getArmyLog();
        Assert.assertNotNull(log);
        boolean success = logDAO.insert(log) > 0;
        getUseCaseCallback().onCompleted(ComplexResponse.get(success));
    }

    public static class RequestValues extends UseCase.RequestValues{
        private ArmyLog armyLog;

        public RequestValues(ArmyLog armyLog) {
            this.armyLog = armyLog;
        }

        public ArmyLog getArmyLog() {
            return armyLog;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
