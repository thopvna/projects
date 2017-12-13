package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.armies.SFieldTroop;
import com.thopv.projects.ikariam.home.presentation.models.ModelFieldTroop;

import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */
@Dao
public interface FieldTroopDAO {
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select party, viewId, unitName, amount, currentHitPoints, currentMunitions, maxAmount " +
            " from SFieldTroop")
    LiveData<List<ModelFieldTroop>> getAllLive();

    @Query("select * from SFieldTroop where amount > 0 and currentHitPoints > 0")
    List<SFieldTroop> getAllHasHitPoints();
    @Query("select * from SFieldTroop where party = :party")
    List<SFieldTroop> getAll(int party);
    @Query("select * from SFieldTroop where party = :party and amount > 0 and currentHitPoints > 0")
    List<SFieldTroop> getAllAvailable(int party);
    @Query("select * from SFieldTroop where amount > 0")
    List<SFieldTroop> getAllAvailable();

    @Delete
    int deleteAll(List<SFieldTroop> troops);
    @Delete
    int delete(SFieldTroop troop);

    @Update
    int updateAll(List<SFieldTroop> troops);
    @Update
    int update(SFieldTroop troop);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<SFieldTroop> troops);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SFieldTroop sFieldTroop);

}
