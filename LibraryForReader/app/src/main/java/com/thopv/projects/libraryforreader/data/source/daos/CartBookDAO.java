package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.libraryforreader.data.source.schemas.SCartBook;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface CartBookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SCartBook cartBook);

    @Update
    int update(SCartBook cartBook);

    @Delete
    int delete(SCartBook cartBook);
    @Query("delete from scartbook where bookId = :cartBookId")
    int delete(Long cartBookId);

    @Query("delete from scartbook")
    int clearAll();

    @Query("select * from scartbook")
    List<CartBook> fetchAll();
    @Query("select * from scartbook where bookId = :id")
    CartBook fetchById(long id);
}
