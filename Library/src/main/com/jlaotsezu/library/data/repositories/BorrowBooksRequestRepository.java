package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.BorrowBooksRequestDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindNonConfirmBorrowBooksRequests;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBorrowBooksRequestsByUserId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BorrowBooksRequestRepository implements Repository<Integer, BorrowBooksRequest> {
    @Autowired
    BorrowBooksRequestDAO borrowBooksRequestDAO;
    @Override
    public boolean save(BorrowBooksRequest borrowBooksRequest) {
        return borrowBooksRequestDAO.save(borrowBooksRequest);
    }

    @Override
    public boolean saveAll(List<BorrowBooksRequest> borrowBooksRequests) {
        return borrowBooksRequestDAO.saveAll(borrowBooksRequests);
    }
    @Override
    public boolean update(BorrowBooksRequest borrowBooksRequest) {
        return borrowBooksRequestDAO.update(borrowBooksRequest);
    }

    @Override
    public boolean updateAll(List<BorrowBooksRequest> borrowBooksRequests) {
        return borrowBooksRequestDAO.updateAll(borrowBooksRequests);
    }

    @Override
    public boolean delete(BorrowBooksRequest borrowBooksRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(BorrowBooksRequest borrowBooksRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAllById(List<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BorrowBooksRequest fetchById(Integer integer) {
        return borrowBooksRequestDAO.fetchById(integer);
    }

    @Override
    public List<BorrowBooksRequest> fetchAll() {
        return borrowBooksRequestDAO.fetchAll();
    }

    @Override
    public List<BorrowBooksRequest> find(Specification specification) {
        if(specification instanceof FindBorrowBooksRequestsByUserId){
            return borrowBooksRequestDAO.findByUserId(((FindBorrowBooksRequestsByUserId) specification).getUserId());
        }
        else if(specification instanceof FindNonConfirmBorrowBooksRequests){
            return borrowBooksRequestDAO.fetchAllNonConfirm();
        }
        throw new UnsupportedOperationException();
    }
}
