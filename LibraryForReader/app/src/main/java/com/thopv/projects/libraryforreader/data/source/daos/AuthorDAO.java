package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.thopv.projects.libraryforreader.data.source.schemas.SAuthor;
import com.thopv.projects.libraryforreader.home.domain.entity.Author;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface AuthorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SAuthor sauthor);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<SAuthor> sauthors);

    @Delete
    int delete(SAuthor sauthor);
    @Delete
    int deleteAll(List<SAuthor> sauthors);

    @Query("delete from sauthor where authorId = :authorId ")
    int delete(long authorId);

    @Query("select * from sauthor where authorId = :authorId ")
    Author fetchById(long authorId);
}
