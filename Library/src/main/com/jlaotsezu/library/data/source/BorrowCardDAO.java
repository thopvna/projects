package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;

import java.util.List;

public interface BorrowCardDAO {
    List<BorrowCard> findByUserId(int userId);
    List<BorrowCard> fetchAll();
    BorrowCard fetchById(Integer id);
    boolean saveAll(List<BorrowCard> borrowCards);
    boolean save(BorrowCard borrowCard);

    boolean updateAll(List<BorrowCard> borrowCards);

    boolean update(BorrowCard borrowCard);

    void clearAll();

    List<BorrowCard> findByKeyword(String keyword);

}
