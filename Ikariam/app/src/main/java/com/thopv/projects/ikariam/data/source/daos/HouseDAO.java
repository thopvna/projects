package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.houses.House;

import java.util.List;

/**
 * Created by thopv on 11/13/2017.
 */
@Dao
public interface HouseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(House house);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] addAll(List<House> houses);

    @Update
    int update(House house);
    @Update
    int updateAll(List<House> houses);

    @Delete
    int delete(House house);
    @Delete
    int deleteAll(List<House> houses);
    @Query("delete from house where party = :party")
    int delete(int party);

    @Query("select * from house")
    List<House> getAlls();

    @Query("select * from house")
    LiveData<List<House>> getLives();
    @Query("select * from house where party = :party")
    House get(int party);
}
