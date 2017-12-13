package com.thopv.projects.ikariam.fight.presentation.contracts;

import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class UnitsSendContract {
    public interface Presenter{
        void sendUnits(long goTime, List<BaseTroop> baseTroops);
    }
    public interface Controller {
        void showMessage(String msg);
        void showUnits(List<BaseTroop> homeTroops);
        void cancel();
    }
}
