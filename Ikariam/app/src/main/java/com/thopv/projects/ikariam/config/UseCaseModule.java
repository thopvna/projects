package com.thopv.projects.ikariam.config;

import com.thopv.projects.ikariam.data.source.daos.FightStatusDAO;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.data.source.repositories.AttackTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.CollosusRepository;
import com.thopv.projects.ikariam.data.source.repositories.CollosusedTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.FieldTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.HomeTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.HouseRepository;
import com.thopv.projects.ikariam.data.source.repositories.LoRepository;
import com.thopv.projects.ikariam.data.source.repositories.MovingTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.PopulableTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.UnablePopulableTroopRepository;
import com.thopv.projects.ikariam.data.source.repositories.WhaleRepository;
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

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 11/20/2017.
 */
@Module
public class UseCaseModule {
    @Provides
    GetHouse getHouse(HouseRepository houseRepository){
        return new GetHouse(houseRepository);
    }
    @Provides
    AddHouse addHouse(HouseRepository houseRepository){
        return new AddHouse(houseRepository);
    }
    @Provides
    RemoveHouse removeHouse(HouseRepository houseRepository){
        return new RemoveHouse(houseRepository);
    }
    @Provides
    SetupHomeUnits addHomeTroops(HomeTroopRepository homeTroopRepository){
        return new SetupHomeUnits(homeTroopRepository);
    }
    @Provides
    SendUnits sendUnits(HomeTroopRepository homeTroopRepository, MovingTroopRepository movingTroopRepository, LogDAO logDAO, WhaleRepository whaleRepository){
        return new SendUnits(movingTroopRepository, homeTroopRepository, whaleRepository, logDAO);
    }
    @Provides
    ConfirmMovingTroops confirmMovingTroops(AttackTroopRepository attackTroopRepository, MovingTroopRepository movingTroopRepository, LogDAO logDAO, HomeTroopRepository homeTroopRepository){
        return new ConfirmMovingTroops(movingTroopRepository, attackTroopRepository, homeTroopRepository);
    }
    @Provides
    AddLog addLog(LogDAO logDAO){
        return new AddLog(logDAO);
    }
    @Provides
    GetLogDetail getLogDetail(MovingTroopRepository movingTroopRepository, CollosusedTroopRepository collosusedTroopRepository){
        return new GetLogDetail(movingTroopRepository, collosusedTroopRepository);
    }
    @Provides
    GetTroopCount getTroopCount(HomeTroopRepository homeTroopRepository){
        return new GetTroopCount(homeTroopRepository);
    }
    @Provides
    LoadHomeTroops loadHomeTroops(HomeTroopRepository homeTroopRepository){
        return new LoadHomeTroops(homeTroopRepository);
    }
    @Provides
    LoadHouses loadHouses(HouseRepository houseRepository){
        return new LoadHouses(houseRepository);
    }
    @Provides
    LoadLogs loadLogs(LogDAO logDAO){
        return new LoadLogs(logDAO);
    }
    @Provides
    ReturnUnits returnUnits(FieldTroopRepository fieldTroopRepository, MovingTroopRepository movingTroopRepository, AttackTroopRepository attackTroopRepository, WhaleRepository whaleRepository, UnablePopulableTroopRepository unablePopulateTroopRepository, LogDAO logDAO){
        return new ReturnUnits(fieldTroopRepository, movingTroopRepository, attackTroopRepository, whaleRepository, unablePopulateTroopRepository, logDAO);
    }
    @Provides
    PopulateUnits populateUnits(PopulableTroopRepository populableTroopRepository, UnablePopulableTroopRepository unablePopulateTroopRepository,  FieldTroopRepository fieldTroopRepository, HouseRepository houseRepository, MovingTroopRepository movingTroopRepository, LogDAO logDAO, FightStatusDAO fightStatusDAO){
        return new PopulateUnits(populableTroopRepository, unablePopulateTroopRepository, fieldTroopRepository, houseRepository, movingTroopRepository, logDAO, fightStatusDAO);
    }
    @Provides
    Fight fight(FieldTroopRepository fieldTroopRepository, UnablePopulableTroopRepository unablePopulateTroopRepository,  LoRepository loRepository, HouseRepository houseRepository, FightStatusDAO fightStatusDAO){
        return new Fight(fieldTroopRepository, unablePopulateTroopRepository, loRepository, houseRepository, fightStatusDAO);
    }
    @Provides
    ApplyWhaleEffect applyWhaleEffect(HouseRepository houseRepository, WhaleRepository whaleRepository, MovingTroopRepository movingTroopRepository){
        return new ApplyWhaleEffect(houseRepository, whaleRepository, movingTroopRepository);
    }
    @Provides
    ApplyLoEffect applyLoEffect(HouseRepository houseRepository, LoRepository loRepository){
        return new ApplyLoEffect(houseRepository, loRepository);
    }
    @Provides
    LoadEffectStatus loadEffectStatus(WhaleRepository whaleRepository, LoRepository loRepository){
        return new LoadEffectStatus(whaleRepository, loRepository);
    }
    @Provides
    ClearUnits clearUnits(HomeTroopRepository homeTroopRepository, FieldTroopRepository fieldTroopRepository, AttackTroopRepository attackTroopRepository){
        return new ClearUnits(homeTroopRepository, fieldTroopRepository, attackTroopRepository);
    }
    @Provides
    ApplyCollosusEffect applyCollosusEffect(HouseRepository houseRepository, CollosusRepository collosusRepository, AttackTroopRepository attackTroopRepository, FieldTroopRepository fieldTroopRepository, CollosusedTroopRepository collosusedTroopRepository, LogDAO logDAO){
        return new ApplyCollosusEffect(houseRepository, collosusRepository, attackTroopRepository, fieldTroopRepository, collosusedTroopRepository, logDAO);
    }
    @Provides
    ConfirmCollosusTroops confirmCollosusTroops(CollosusedTroopRepository collosusedTroopRepository, AttackTroopRepository attackTroopRepository){
        return new ConfirmCollosusTroops(collosusedTroopRepository, attackTroopRepository);
    }
    @Provides
    LoadAttackUnits loadAttackUnits(AttackTroopRepository attackTroopRepository, FieldTroopRepository fieldTroopRepository, UnablePopulableTroopRepository unablePopulateTroopRepository){
        return new LoadAttackUnits(attackTroopRepository, fieldTroopRepository, unablePopulateTroopRepository);
    }
}
