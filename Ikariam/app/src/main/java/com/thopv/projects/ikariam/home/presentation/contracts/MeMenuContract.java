package com.thopv.projects.ikariam.home.presentation.contracts;

import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogDetail;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class MeMenuContract {
    public interface Presenter{
        void setupUnits(List<BaseTroop> troopsInform);
        void loadLogs();
        void loadBattleField();
        void loadDetailLog(ArmyLog log);
        void loadTroops();
        void removeHouse();
        void applyWhaleEffect();
        void applyLoEffect();
        void loadEffectStatus();
        void loadHomeTroops();
        void clearUnits();
    }
    public interface Controller {
        void showHouse(House house);
        void showUnitsAmount(int unitsAmount);
        void showUnits(List<HomeTroop> homeTroops);
        void showUnitsSetup(List<BaseTroop> baseTroops);
        void showEffectStatus(String status);
        void showBattleField(House house);
        void showLogs(List<ArmyLog> logs);
        void showLogDetail(ArmyLogDetail log);
        void showError(String err);
        void cancel();
    }
}
