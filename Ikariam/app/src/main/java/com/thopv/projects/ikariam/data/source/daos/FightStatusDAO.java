package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/29/2017.
 */
@Dao
public interface FightStatusDAO {
    @Query("select * from fightstatus where id = 1")
    LiveData<FightStatus> fetchLive();
    @Query("select * from fightstatus where id = 1")
    FightStatus fetch();
    @Update
    int update(FightStatus fightStatus);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FightStatus fightStatus);
}
