package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;

import java.util.List;

public interface LoanDAO {
    List<Loan> findByUserId(int userId);
    List<Loan> fetchAll();
    Loan fetchById(Integer id);
    boolean save(Loan loan);

    boolean update(Loan loan);

    void clearAll();

    List<Loan> findNonReturnByUserId(int userId);

    boolean deleteById(int id);
}
