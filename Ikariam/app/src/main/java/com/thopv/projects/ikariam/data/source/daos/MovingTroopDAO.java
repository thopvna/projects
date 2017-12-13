package com.thopv.projects.ikariam.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */
@Dao
public interface MovingTroopDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MovingTroop troop);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<MovingTroop> troops);

    @Update
    int update(MovingTroop troop);
    @Update
    int updateAll(List<MovingTroop> troops);

    @Delete
    int delete(MovingTroop movingTroop);
    @Delete
    int deleteAll(List<MovingTroop> movingTroops);

    @Query("select * from MovingTroop where endTime < :currentTimeInMillis and isConfirm = :confirm")
    List<MovingTroop> getAllMovingTroops(long currentTimeInMillis, boolean confirm);
    @Query("select * from MovingTroop where startTime = :timeInMillis")
    List<MovingTroop> getAllMovingTroopsStartAt(long timeInMillis);

    @Query("select * from MovingTroop where " +
            " startTime = :startTime " +
            " and unitName = :unitName " +
            " limit 1 ")
    MovingTroop get(long startTime, String unitName);
}
