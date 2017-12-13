package com.thopv.projects.ikariam.data.source.repositories;
import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public interface Repository<I,T> {
    boolean insert(T entity);
    boolean insertAll(List<T> entities);
    boolean delete(I id);
    boolean deleteAll(List<I> ids);
    boolean update(T entity);
    boolean updateAll(List<T> entities);
    List<T> loadAll();
    T findById(I id);
    <Q extends Criteria> List<T> find(Q criteria);
    interface Criteria {}
    void runInTransaction(Runnable runnable);
    void beginTransaction();
    void endTransaction();
    void commitTransaction();
}
