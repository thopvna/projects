package com.thopv.projects.ikariam.config;

import android.app.NotificationManager;

import com.thopv.projects.ikariam.data.source.daos.FightStatusDAO;
import com.thopv.projects.ikariam.data.source.daos.HomeTroopDAO;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.data.source.daos.UnablePopulableTroopDAO;
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

import dagger.Component;

/**
 * Created by thopv on 11/20/2017.
 */
@RepositoryScope
@Component(modules = RepositoryModule.class, dependencies = {ApplicationComponent.class})
public interface RepositoryComponent {
    AttackTroopRepository getAttackRepository();
    FieldTroopRepository getFieldTroopRepository();
    HomeTroopRepository getHomeTroopRepository();
    HouseRepository getHouseRepository();
    MovingTroopRepository getMovingTroopRepository();
    PopulableTroopRepository getPopulableTroopRepository();
    WhaleRepository getWhaleRepository();
    LoRepository getLoRepository();
    LogDAO getLogDAO();
    HomeTroopDAO getHomeTroopDAO();
    CollosusRepository getCollosusRepository();
    CollosusedTroopRepository getCollosusedTroopRepository();
    NotificationManager getNotificationManager();
    FightStatusDAO getFightDAO();
    UnablePopulableTroopRepository getUnablePopulableTroopRepository();
}
