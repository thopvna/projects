package com.thopv.projects.ikariam.config;

import android.content.Context;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.FightStatusDAO;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.data.source.daos.HomeTroopDAO;
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

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 11/20/2017.
 */
@Module
public class RepositoryModule {
    @Provides
    @RepositoryScope
    AppDatabase appDatabase(Context context){
        return AppDatabase.getInstance(context);
    }
    @Provides
    @RepositoryScope
    LogDAO logDAO(AppDatabase appDatabase){
        return appDatabase.getLogDAO();
    }
    @Provides
    @RepositoryScope
    HomeTroopDAO troopDAO(AppDatabase appDatabase){
        return appDatabase.getTroopDAO();
    }
    @Provides
    @RepositoryScope
    HomeTroopRepository homeTroopRepository(AppDatabase appDatabase){
        return new HomeTroopRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    FieldTroopRepository fieldTroopRepository(AppDatabase appDatabase){
        return new FieldTroopRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    AttackTroopRepository attackTroopRepository(AppDatabase appDatabase){
        return new AttackTroopRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    HouseRepository houseRepository(AppDatabase appDatabase){
        return new HouseRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    MovingTroopRepository movingTroopRepository(AppDatabase appDatabase){
        return new MovingTroopRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    PopulableTroopRepository populableTroopRepository(AppDatabase appDatabase){
        return new PopulableTroopRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    WhaleRepository whaleRepository(AppDatabase appDatabase){
        return new WhaleRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    LoRepository loRepository(AppDatabase appDatabase){
        return new LoRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    CollosusedTroopRepository collosusedTroopRepository(AppDatabase appDatabase){
        return new CollosusedTroopRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    CollosusRepository collosusRepository(AppDatabase appDatabase){
        return new CollosusRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    FightStatusDAO fightDAO(AppDatabase appDatabase){
        return appDatabase.getFightStatusDAO();
    }
    @Provides
    @RepositoryScope
    UnablePopulableTroopRepository unablePopulableTroopRepository(AppDatabase appDatabase){
        return new UnablePopulableTroopRepository(appDatabase);
    }
}
