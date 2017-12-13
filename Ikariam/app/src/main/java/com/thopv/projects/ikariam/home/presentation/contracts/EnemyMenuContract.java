package com.thopv.projects.ikariam.home.presentation.contracts;

import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class EnemyMenuContract {
    public interface Presenter{
        void setupUnits(List<BaseTroop> troopsInform);
        void removeHouse();
        void applyWhaleEffect();
        void applyLoEffect();
        void applyCollosusEffect();
        void loadEffectStatus();
        void loadHomeTroops();
        void clearUnits();
    }
    public interface Controller {
        void showUnitsAmount(int unitsAmount);
        void showEffectStatus(String status);
        void showUnitsSetup(List<BaseTroop> troops);
        void showError(String err);
        void cancel();
    }
}
