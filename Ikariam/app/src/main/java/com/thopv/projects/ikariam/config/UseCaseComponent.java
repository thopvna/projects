package com.thopv.projects.ikariam.config;

import android.content.Context;

import com.thopv.projects.ikariam.fight.domain.usecases.Fight;
import com.thopv.projects.ikariam.fight.domain.usecases.LoadAttackUnits;
import com.thopv.projects.ikariam.fight.domain.usecases.PopulateUnits;
import com.thopv.projects.ikariam.fight.domain.usecases.ReturnUnits;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyCollosusEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ClearUnits;
import com.thopv.projects.ikariam.home.domain.usecases.ConfirmCollosusTroops;
import com.thopv.projects.ikariam.home.domain.usecases.SetupHomeUnits;
import com.thopv.projects.ikariam.home.domain.usecases.AddHouse;
import com.thopv.projects.ikariam.home.domain.usecases.AddLog;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyLoEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyWhaleEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ConfirmMovingTroops;
import com.thopv.projects.ikariam.home.domain.usecases.GetHouse;
import com.thopv.projects.ikariam.home.domain.usecases.GetLogDetail;
import com.thopv.projects.ikariam.home.domain.usecases.GetTroopCount;
import com.thopv.projects.ikariam.home.domain.usecases.LoadEffectStatus;
import com.thopv.projects.ikariam.home.domain.usecases.LoadHomeTroops;
import com.thopv.projects.ikariam.home.domain.usecases.LoadHouses;
import com.thopv.projects.ikariam.home.domain.usecases.LoadLogs;
import com.thopv.projects.ikariam.home.domain.usecases.RemoveHouse;
import com.thopv.projects.ikariam.home.domain.usecases.SendUnits;

import dagger.Component;

/**
 * Created by thopv on 11/20/2017.
 */
@UseCaseScope
@Component(modules = {UseCaseModule.class}, dependencies = {RepositoryComponent.class})
public interface UseCaseComponent {
    SetupHomeUnits getAddHomeTroops();
    AddHouse getAddHouse();
    AddLog getAddLog();
    GetHouse getGetHouse();
    GetLogDetail getGetLogDetail();
    GetTroopCount getGetTroopCount();
    LoadHomeTroops getLoadHomeTroops();
    LoadHouses getLoadHouses();
    LoadLogs getLoadLogs();
    RemoveHouse getRemoveHouse();
    SendUnits getSendUnits();
    ReturnUnits getReturnUnits();
    PopulateUnits getPopulateUnits();
    ConfirmMovingTroops getConfirmMovingTroops();
    Fight getFight();
    ApplyWhaleEffect getApplyWhaleEffect();
    ApplyLoEffect getApplyLoEffect();
    LoadEffectStatus getLoadEffectStatus();
    ApplyCollosusEffect getApplyCollosusEffect();
    ConfirmCollosusTroops getConfirmCollosusTroops();
    ClearUnits getClearUnits();
    LoadAttackUnits getLoadAttackUnits();
    static UseCaseComponent getInstance(Context context){
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(context))
                .build();
        RepositoryComponent repositoryComponent  = DaggerRepositoryComponent.builder()
                .applicationComponent(applicationComponent)
                .build();
        return DaggerUseCaseComponent.builder()
                .repositoryComponent(repositoryComponent)
                .build();
    }

}
