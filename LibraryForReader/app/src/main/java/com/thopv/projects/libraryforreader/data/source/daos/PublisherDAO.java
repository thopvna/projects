package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.thopv.projects.libraryforreader.data.source.schemas.SPublisher;
import com.thopv.projects.libraryforreader.home.domain.entity.Publisher;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface PublisherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SPublisher publisher);
    
    @Delete
    int delete(SPublisher publisher);
    
    @Query("delete from spublisher where publisherId = :publisherId ")
    int delete(long publisherId);
    
    @Query("select * from spublisher where publisherId = :publisherId ")
    Publisher fetchById(long publisherId);
}
