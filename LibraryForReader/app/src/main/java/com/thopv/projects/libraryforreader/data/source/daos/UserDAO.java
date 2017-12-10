package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.libraryforreader.data.source.schemas.SUser;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SUser user);

    @Update
    int update(SUser user);

    @Delete
    int delete(SUser user);

    @Query("select * from suser where userName = :userName ")
    User fetchById(String userName);
}
