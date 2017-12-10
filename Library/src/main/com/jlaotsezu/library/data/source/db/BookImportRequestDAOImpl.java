package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.BookImportRequestDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookImportRequestDAOImpl implements BookImportRequestDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public List<BookImportRequest> fetchAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> session.createCriteria(BookImportRequest.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list());
    }

    @Override
    public BookImportRequest fetchById(int id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> (BookImportRequest)
                session.createCriteria(BookImportRequest.class)
                .add(Restrictions.eq("bookImportRequestId", id))
                .uniqueResult());
    }


    @Override
    public boolean save(BookImportRequest bookImportRequest) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.save(bookImportRequest);
            return bookImportRequest.getBookImportRequestId() > 0;
        });
    }
    @Override
    public boolean saveAll(List<BookImportRequest> bookImportRequests) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            boolean success = true;
            for(int i = 0; i < bookImportRequests.size(); i++){
                BookImportRequest request = bookImportRequests.get(i);
                session.save(request);
                if(request.getBookImportRequestId() <= 0){
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
    public boolean update(BookImportRequest bookImportRequest) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.update(bookImportRequest);
            return bookImportRequest.getBookImportRequestId() > 0;
        });
    }
    @Override
    public boolean updateAll(List<BookImportRequest> bookImportRequests) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            boolean success = true;
            for(int i = 0; i < bookImportRequests.size(); i++){
                BookImportRequest request = bookImportRequests.get(i);
                session.update(request);
                if(request.getBookImportRequestId() <= 0){
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
            session.createQuery("delete from BookImportRequest ").executeUpdate();
            return true;
        });
    }

    @Override
    public List<BookImportRequest> fetchByLibrarianId(int librarianId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> session.createCriteria(BookImportRequest.class)
                .createCriteria("librarian")
                .add(Restrictions.eq("userId", librarianId))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list());
    }

    @Override
    public boolean delete(int requestId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> session.createQuery("delete from BookImportRequest where bookImportRequestId = :bookImportRequestId")
                .setParameter("bookImportRequestId", requestId)
                .executeUpdate() > 0);
    }
}
