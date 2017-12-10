package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;

import java.util.List;

public interface BorrowBooksRequestDAO {
    List<BorrowBooksRequest> findByUserId(int userId);
    List<BorrowBooksRequest> fetchAllNonConfirm();
    BorrowBooksRequest fetchById(Integer id);
    List<BorrowBooksRequest> fetchAll();
    boolean save(BorrowBooksRequest borrowBooksRequest);
    boolean saveAll(List<BorrowBooksRequest> borrowBooksRequests);

    boolean update(BorrowBooksRequest borrowBooksRequest);

    boolean updateAll(List<BorrowBooksRequest> borrowBooksRequests);

    void clearAll();

}
