package com.thopv.projects.ikariam.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.home.domain.entity.Collosus;

/**
 * Created by thopv on 11/27/2017.
 */
@Dao
public interface CollosusDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Collosus collosus);
    @Update
    int update(Collosus collosus);
    @Query("select * from collosus where party = :party")
    Collosus fetchById(int party);
}
