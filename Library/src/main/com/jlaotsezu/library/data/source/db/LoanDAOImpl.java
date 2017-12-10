package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.LoanDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoanDAOImpl implements LoanDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public List<Loan> findByUserId(int userId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(Loan.class)
                        .add(Restrictions.eq("borrower.userId", userId))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());
    }

    @Override
    public List<Loan> fetchAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(Loan.class)
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());
    }

    @Override
    public Loan fetchById(Integer id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                (Loan)session.createCriteria(Loan.class)
                            .add(Restrictions.eq("loanId", id))
                            .uniqueResult());
    }

    @Override
    public boolean save(Loan loan) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.save(loan);
            return loan.getLoanId() > 0;
        });
    }
    @Override
    public boolean update(Loan loan) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.update(loan);
            return loan.getLoanId() > 0;
        });
    }
    @Override
    public void clearAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        sessionHandler.runCUD(session -> {
            session.createQuery("delete from Loan").executeUpdate();
            return true;
        });
    }

    @Override
    public List<Loan> findNonReturnByUserId(int userId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(Loan.class)
                        .add(Restrictions.eq("isReturn", false))
                        .createCriteria("borrower")
                        .add(Restrictions.eq("userId", userId))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());
    }

    @Override
    public boolean deleteById(int id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> session
                .createQuery("delete from Loan where loanId = :loanId")
                .setParameter("loanId", id)
                .executeUpdate() > 0);
    }

}
