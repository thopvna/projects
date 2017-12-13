package com.thopv.projects.ikariam.data.source.daos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.thopv.projects.ikariam.data.schema.armies.CollosusedTroop;
import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.schema.armies.SFieldTroop;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStatus;
import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.domain.entity.Collosus;
import com.thopv.projects.ikariam.home.domain.entity.Lo;
import com.thopv.projects.ikariam.home.domain.entity.Whale;
import com.thopv.projects.ikariam.data.schema.users.User;

/**
 * Created by thopv on 11/13/2017.
 */
@Database(entities = {UnablePopulateTroop.class, FightStatus.class, Collosus.class, CollosusedTroop.class, Whale.class, Lo.class, ArmyLog.class, House.class, User.class, HomeTroop.class, AttackTroop.class, SFieldTroop.class, MovingTroop.class}, version =  2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ikariam")
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract LoDAO getLoDAO();
    public abstract WhaleDAO getWhaleDAO();
    public abstract MovingTroopDAO getMovingDAO();
    public abstract HomeTroopDAO getTroopDAO();
    public abstract HouseDAO getHouseDAO();
    public abstract LogDAO getLogDAO();
    public abstract AttackTroopDAO getAttackTroopDAO();
    public abstract FieldTroopDAO getFieldTroopsDAO();
    public abstract CollosusDAO getCollosusDAO();
    public abstract CollosusedTroopDAO getCollosusedTroopDAO();
    public abstract FightStatusDAO getFightStatusDAO();
    public abstract UnablePopulableTroopDAO getUnablePopulableTroopDAO();
}
