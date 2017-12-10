package com.thopv.projects.libraryforreader.data.source;

import java.util.List;

/**
 * Created by jlaotsezu on 23/11/2017.
 */

public interface Repository<ID, ENTITY> {
    boolean save(ENTITY entity);

    boolean delete(ENTITY entity);

    boolean clearAll();

    List<ENTITY> fetchAll();

    ENTITY findById(ID id);
    List<ENTITY> find(Specification specification);


    void commitTransaction();
    void runInTransacstion(Runnable runnable);
}
