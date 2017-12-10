package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.libraryforreader.data.source.schemas.SBook;
import com.thopv.projects.libraryforreader.data.source.schemas.SBookAuthorDetail;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SBook sBook);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertDetails(List<SBookAuthorDetail> sBookAuthorDetails);

    @Update
    int update(SBook sBook);
    @Update
    int updateDetails(List<SBookAuthorDetail> sBookAuthorDetails);

    @Delete
    int delete(SBook sBook);
    @Delete
    int delete(List<SBookAuthorDetail> sBookAuthorDetails);

    @Query("delete from sbook")
    int clearAllBook();
    @Query("delete from sbookauthordetail")
    int clearAllDetail();

    @Query("select * from sbook where bookId = :id")
    SBook fetchById(long id);
    @Query("select * from sbook")
    List<SBook> fetchAll();
    @Query("select * from sbookauthordetail where bookId = :bookId")
    List<SBookAuthorDetail> fetchAllOf(long bookId);

    @Query("select * from sbook where title like :keyword ")
    List<SBook> findByTitle(String keyword);
    @Query("select sbook.* from sbook where " +
            " briefContent like :keyword ")
    List<SBook> findByBriefContent(String keyword);
    @Query("select sbook.* from sbook where " +
            " publishTime like :keyword ")
    List<SBook> findByPublisherTime(String keyword);

    @Query("select sbook.* from sbook, sbookauthordetail, sauthor where " +
            " sbookauthordetail.authorId = sauthor.authorId" +
            " and sbook.bookId = sbookauthordetail.bookId " +
            " and sauthor.authorName like :keyword ")
    List<SBook> findByAuthorName(String keyword);

    @Query("select sbook.* from sbook, sclassification where " +
            " sbook.classificationId = sclassification.classificationId" +
            " and sclassification.classificationName like :keyword " +
            " ")
    List<SBook> findByClassificationName(String keyword);

    @Query("select sbook.* from sbook, spublisher where " +
            " sbook.publisherId = spublisher.publisherId" +
            " and spublisher.publisherName like :keyword" +
            " ")
    List<SBook> findByPublisherName(String keyword);

}
