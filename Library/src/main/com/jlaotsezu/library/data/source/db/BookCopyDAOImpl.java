package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.BookCopyDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookCopyDAOImpl implements BookCopyDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public boolean save(BookCopy bookCopy) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.save(bookCopy);
            return bookCopy.getBookCopyId() > 0;
        });
    }

    @Override
    public boolean saveAll(List<BookCopy> bookCopies) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            boolean success = true;
            for(int i =0; i < bookCopies.size(); i++){
                BookCopy bookCopy = bookCopies.get(i);
                session.save(bookCopy);
                if(bookCopy.getBookCopyId() <= 0) {
                    success = false;
                    break;
                }
                if((i + 1) % SessionHandler.BATCH_SIZE == 0){
                    session.flush();
                    session.clear();
                }
            }
            return success;
        });
    }
    @Override
    public boolean update(BookCopy bookCopy) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.update(bookCopy);
            return bookCopy.getBookCopyId() > 0;
        });
    }

    @Override
    public boolean updateAll(List<BookCopy> bookCopies) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            boolean success = true;
            for(int i =0; i < bookCopies.size(); i++){
                BookCopy bookCopy = bookCopies.get(i);
                session.update(bookCopy);
                if(bookCopy.getBookCopyId() <= 0) {
                    success = false;
                    break;
                }
                if((i + 1) % SessionHandler.BATCH_SIZE == 0){
                    session.flush();
                    session.clear();
                }
            }
            return success;
        });
    }
    @Override
    public List<BookCopy> fetchAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> session.createCriteria(BookCopy.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list());
    }

    @Override
    public BookCopy fetchById(int id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> {
            return (BookCopy)
                    session.createCriteria(BookCopy.class)
                    .add(Restrictions.eq("bookCopyId", id))
                    .uniqueResult();
        });
    }


    @Override
    public void clearAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        sessionHandler.runCUD(session -> {
            session.createQuery("delete from BookCopy ").executeUpdate();
            return true;
        });
    }

}
