package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.LoanDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindLoanByUserId;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindNonReturnLoanByUserId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoanRepository implements Repository<Integer, Loan> {
    @Autowired
    LoanDAO loanDAO;
    @Override
    public boolean save(Loan loan) {
        return loanDAO.save(loan);
    }

    @Override
    public boolean saveAll(List<Loan> loans) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Loan loan) {
        return loanDAO.update(loan);
    }

    @Override
    public boolean updateAll(List<Loan> loans) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Loan loan) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(Loan loan) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Integer integer) {
        int id = integer;
        return loanDAO.deleteById(id);
    }

    @Override
    public boolean deleteAllById(List<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Loan fetchById(Integer integer) {
        return loanDAO.fetchById(integer);
    }

    @Override
    public List<Loan> fetchAll() {
        return loanDAO.fetchAll();
    }

    @Override
    public List<Loan> find(Specification specification) {
        if(specification instanceof FindLoanByUserId){
            return loanDAO.findByUserId(((FindLoanByUserId) specification).getUserId());
        }
        else if(specification instanceof FindNonReturnLoanByUserId){
            return loanDAO.findNonReturnByUserId(((FindNonReturnLoanByUserId) specification).getUserId());
        }
       throw new UnsupportedOperationException();
    }
}
