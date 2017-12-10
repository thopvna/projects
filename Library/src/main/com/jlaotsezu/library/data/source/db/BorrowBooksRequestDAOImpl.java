package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.BorrowBooksRequestDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BorrowBooksRequestDAOImpl implements BorrowBooksRequestDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public List<BorrowBooksRequest> findByUserId(int userId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(BorrowBooksRequest.class)
                           .add(Restrictions.eq("borrower.userId", userId))
                            .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                           .list());
    }

    @Override
    public List<BorrowBooksRequest> fetchAllNonConfirm() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(BorrowBooksRequest.class)
                        .add(Restrictions.eq("isConfirm", false))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());
    }

    @Override
    public BorrowBooksRequest fetchById(Integer id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                (BorrowBooksRequest)
                    session.createCriteria(BorrowBooksRequest.class)
                    .add(Restrictions.eq("borrowBooksRequestId", id))
                    .uniqueResult());
    }

    @Override
    public List<BorrowBooksRequest> fetchAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(BorrowBooksRequest.class)
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                       .list());
    }

    @Override
    public boolean save(BorrowBooksRequest borrowBooksRequest) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.save(borrowBooksRequest);
            return borrowBooksRequest.getBorrowBooksRequestId() > 0;
        });
    }

    @Override
    public boolean saveAll(List<BorrowBooksRequest> borrowBooksRequests) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session ->  {
            boolean success = true;

            for(int i = 0; i < borrowBooksRequests.size(); i++){
                BorrowBooksRequest request = borrowBooksRequests.get(i);
                session.save(request);
                if(request.getBorrowBooksRequestId() <= 0){
                    success = false;
                    break;
                }
                if((i + 1) % SessionHandler.BATCH_SIZE   == 0){
                    session.flush();
                    session.clear();
                }
            }

            return success;
        });
    }
    @Override
    public boolean update(BorrowBooksRequest borrowBooksRequest) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.update(borrowBooksRequest);
            return borrowBooksRequest.getBorrowBooksRequestId() > 0;
        });
    }

    @Override
    public boolean updateAll(List<BorrowBooksRequest> borrowBooksRequests) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session ->  {
            boolean success = true;

            for(int i = 0; i < borrowBooksRequests.size(); i++){
                BorrowBooksRequest request = borrowBooksRequests.get(i);
                session.update(request);
                if(request.getBorrowBooksRequestId() <= 0){
                    success = false;
                    break;
                }
                if((i + 1) % SessionHandler.BATCH_SIZE   == 0){
                    session.flush();
                    session.clear();
                }
            }

            return success;
        });
    }
    @Override
    public void clearAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        sessionHandler.runCUD(session -> {
            session.createQuery("delete from BorrowBooksRequest ").executeUpdate();
            return true;
        });
    }

}
