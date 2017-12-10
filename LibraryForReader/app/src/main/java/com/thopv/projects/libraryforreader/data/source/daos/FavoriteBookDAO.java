package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.libraryforreader.data.source.schemas.SFavoriteBook;
import com.thopv.projects.libraryforreader.data.source.schemas.SFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface FavoriteBookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SFavoriteBook favoriteBook);
    
    @Update
    int update(SFavoriteBook favoriteBook);
    
    @Delete
    int delete(SFavoriteBook favoriteBook);
    @Query("delete from sfavoritebook where bookId = :favoriteBookId")
    int delete(Long favoriteBookId);
    
    @Query("delete from sfavoritebook")
    int clearAll();
    
    @Query("select * from sfavoritebook")
    List<FavoriteBook> fetchAll();

    @Query("select * from sfavoritebook where bookId = :id")
    FavoriteBook fetchById(Long id);
}
