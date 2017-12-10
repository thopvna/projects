package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.BorrowCardDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBorrowCardByUserId;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.specifications.FindBorrowCardByKeyword;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BorrowCardRepository implements Repository<Integer, BorrowCard> {
    @Autowired
    BorrowCardDAO borrowCardDAO;
    @Override
    public boolean save(BorrowCard borrowCard) {
        return borrowCardDAO.save(borrowCard);
    }

    @Override
    public boolean saveAll(List<BorrowCard> borrowCards) {
        return borrowCardDAO.saveAll(borrowCards);
    }

    @Override
    public boolean update(BorrowCard borrowCard) {
        return borrowCardDAO.update(borrowCard);
    }

    @Override
    public boolean updateAll(List<BorrowCard> borrowCards) {
        return borrowCardDAO.updateAll(borrowCards);
    }

    @Override
    public boolean delete(BorrowCard borrowCard) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(BorrowCard borrowCard) {
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
    public BorrowCard fetchById(Integer integer) {
        return borrowCardDAO.fetchById(integer);
    }

    @Override
    public List<BorrowCard> fetchAll() {
        return borrowCardDAO.fetchAll();
    }

    @Override
    public List<BorrowCard> find(Specification specification) {
        if(specification instanceof FindBorrowCardByUserId){
            return borrowCardDAO.findByUserId(((FindBorrowCardByUserId) specification).getUserId());
        }
        else if(specification instanceof FindBorrowCardByKeyword){
            return borrowCardDAO.findByKeyword(((FindBorrowCardByKeyword) specification).getKeyword());
        }
        throw new UnsupportedOperationException();
    }
}
