package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;

/**
 * Created by thopv on 11/13/2017.
 */
@Dao
public interface HomeTroopDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<HomeTroop> homeTroops);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(HomeTroop homeTroop);

    @Update
    int updateAll(List<HomeTroop> homeTroops);
    @Update
    int updateAll(HomeTroop... homeTroops);
    @Update
    int update(HomeTroop homeTroop);

    @Delete
    int deleteAll(List<HomeTroop> homeTroops);
    @Delete
    int delete(HomeTroop homeTroop);

    @Query("select * from HomeTroop where party = :party and amount > 0  order by amount desc")
    List<HomeTroop> getAllAvailable(int party);
    @Query("select * from HomeTroop where party = :party  order by amount desc")
    List<HomeTroop> getAll(int party);
    @Query("select * from HomeTroop where party = :party and unitName = :unitName  order by amount desc")
    List<HomeTroop> getAll(int party, String unitName);
    @Query("select sum(amount) from HomeTroop where party = :party")
    int count(int party);
    @Query("select * from HomeTroop where party = :party and unitName = :unitName ")
    HomeTroop get(int party, String unitName);



    @Query("select unitName, amount from HomeTroop where party = :party and amount > 0  order by amount desc")
    LiveData<List<BaseTroop>> getAllLive(int party);
}
