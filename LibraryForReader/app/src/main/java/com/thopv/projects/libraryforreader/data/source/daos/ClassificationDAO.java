package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.thopv.projects.libraryforreader.data.source.schemas.SClassification;
import com.thopv.projects.libraryforreader.home.domain.entity.Classification;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface ClassificationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SClassification classification);

    @Delete
    int delete(SClassification classification);

    @Query("delete from sclassification where classificationId = :classificationId ")
    int delete(long classificationId);

    @Query("select * from sclassification where classificationId = :classificationId ")
    Classification fetchById(long classificationId);
}
