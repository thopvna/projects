package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.home.domain.entity.Lo;

import java.util.List;


/**
 * Created by jlaotsezu on 25/11/2017.
 */
@Dao
public interface LoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Lo lo);
    @Update
    int update(Lo lo);
    @Query("select * from lo where party = :party")
    Lo fetchById(int party);

    @Query("select * from lo")
    LiveData<List<Lo>> fetchAllLive();
}
