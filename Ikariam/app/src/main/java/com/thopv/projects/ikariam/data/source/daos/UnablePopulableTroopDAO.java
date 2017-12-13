package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;

/**
 * Created by thopv on 11/30/2017.
 */
@Dao
public interface UnablePopulableTroopDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(UnablePopulateTroop unablePopulateTroop);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<UnablePopulateTroop> unablePopulateTroop);


    @Update
    int update(UnablePopulateTroop unablePopulateTroop);
    @Update
    int updateAll(List<UnablePopulateTroop> unablePopulateTroop);

    @Delete
    int delete(UnablePopulateTroop unablePopulateTroop);
    @Delete
    int deleteAll(List<UnablePopulateTroop> unablePopulateTroop);

    @Query("select * from unablepopulatetroop ")
    List<UnablePopulateTroop> fetchAll();

    @Query("select * from unablepopulatetroop where party = :party")
    List<UnablePopulateTroop> fetchAll(int party);

    @Query("select unitName, amount from unablepopulatetroop where party = :party and amount > 0")
    LiveData<List<BaseTroop>> fetchAllLive(int party);
}
