package main.com.jlaotsezu.library.data;

import java.util.List;

public interface Repository<ID, ENTITY> {
    boolean save(ENTITY entity);
    boolean saveAll(List<ENTITY> entities);
    boolean update(ENTITY entity);
    boolean updateAll(List<ENTITY> entities);
    boolean delete(ENTITY entity);
    boolean deleteAll(ENTITY entity);
    boolean deleteById(ID id);
    boolean deleteAllById(List<ID> ids);
    ENTITY fetchById(ID id);
    List<ENTITY> fetchAll();
    List<ENTITY> find(Specification specification);
}
