package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.home.domain.entity.Whale;

import java.util.List;

/**
 * Created by jlaotsezu on 25/11/2017.
 */
@Dao
public interface WhaleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Whale whale);
    @Update
    int update(Whale whale);
    @Query("select * from whale where party = :party")
    Whale fetchById(int party);
}
