package com.thopv.projects.ikariam.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.armies.CollosusedTroop;

import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */
@Dao
public interface CollosusedTroopDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CollosusedTroop collosusedTroop);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<CollosusedTroop> troops);

    @Update
    int update(CollosusedTroop collosusedTroop);
    @Update
    int updateAll(List<CollosusedTroop> troops);

    @Delete
    int delete(CollosusedTroop collosusedTroop);
    @Delete
    int deleteAll(List<CollosusedTroop> troops);

    @Query("select * from collosusedtroop")
    List<CollosusedTroop> fetchAll();
    @Query("select * from collosusedtroop where endTime < :currentTimeInMillis  and isConfirm = :isConfirm")
    List<CollosusedTroop> fetch(long currentTimeInMillis, boolean isConfirm);
    @Query("select * from collosusedtroop where startTime == :timeInMillis")
    List<CollosusedTroop> fetchStartAt(long timeInMillis);
}
