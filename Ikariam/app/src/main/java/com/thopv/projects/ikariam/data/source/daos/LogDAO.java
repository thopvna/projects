package com.thopv.projects.ikariam.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;

import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */
@Dao
public interface LogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ArmyLog armyLog);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<ArmyLog> armyLogs);

    @Update
    int update(ArmyLog armyLog);
    @Update
    int update(List<ArmyLog> armyLogs);

    @Delete
    int delete(ArmyLog armyLog);
    @Delete
    int deleteAll(List<ArmyLog> armyLogs);

    @Query("select * from armylog")
    List<ArmyLog> getAll();
    @Deprecated
    @Query("delete from armylog")
    int deleteAll();
}
