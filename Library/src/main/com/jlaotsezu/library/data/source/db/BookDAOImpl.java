package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Author;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BookDAOImpl implements BookDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public boolean save(Book book) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            saveOtherOfBook(session, book);
            session.save(book);
            return book.getBookId() > 0;
        });
    }

    /**
     * Thêm các classification, publisher và author trước. VÌ nếu chúng k tồn tại thì k thể save Book đc.
     * @param session
     * @param book
     */
    private void saveOtherOfBook(Session session, Book book){
        session.save(book.getBookClassification());
        session.save(book.getBookPublisher());
        for(Author author : book.getBookAuthors()){
            session.save(author);
        }
    }
    @Override
    public boolean saveAll(List<Book> books) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session ->  {
            boolean success = true;
            for(int i =0; i < books.size(); i++){
                Book book = books.get(i);
                saveOtherOfBook(session, book);
                session.save(book);
                if(book.getBookId() <= 0) {
                    success = false;
                    break;
                }
                if(((i + 1)) % SessionHandler.BATCH_SIZE   == 0){
                    session.flush();
                    session.clear();
                }
            }
            return success;
        });
    }
    @Override
    public boolean update(Book book) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            saveOtherOfBook(session, book);
            session.save(book);
            return book.getBookId() > 0;
        });
    }

    /**
     * Thêm các classification, publisher và author trước. VÌ nếu chúng k tồn tại thì k thể save Book đc.
     * @param session
     * @param book
     */
    private void updateOtherOfBook(Session session, Book book){
        session.update(book.getBookClassification());
        session.update(book.getBookPublisher());
        for(Author author : book.getBookAuthors()){
            session.update(author);
        }
    }
    @Override
    public boolean updateAll(List<Book> books) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session ->  {
            boolean success = true;
            for(int i =0; i < books.size(); i++){
                Book book = books.get(i);
                updateOtherOfBook(session, book);
                session.update(book);
                if(book.getBookId() <= 0) {
                    success = false;
                    break;
                }
                if(((i + 1)) % SessionHandler.BATCH_SIZE   == 0){
                    session.flush();
                    session.clear();
                }
            }
            return success;
        });
    }
    @Override
    public List<Book> fetchAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> session.createCriteria(Book.class)
                .addOrder(Order.asc("bookId"))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list());
    }

    @Override
    public Book fetchById(int id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> {
            return (Book)
                    session.createCriteria(Book.class)
                    .add(Restrictions.eq("bookId", id))
                    .uniqueResult();
        });
    }

    @Override
    public List<Book> searchByKeyword(String keyword) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> {
            Set<Book> setResult = new LinkedHashSet<>();

            setResult.addAll(fetchByContent(session, keyword));
            setResult.addAll(fetchByPublisherYear(session, keyword));
            setResult.addAll(fetchByClassificatioName(session, keyword));
            setResult.addAll(fetchByAuthorName(session, keyword));
            setResult.addAll(fetchByPublisherName(session, keyword));

            return new LinkedList<>(setResult);
        });
    }

    private List<Book> fetchByContent(Session session, String keyword) {
        List<Book> books = session.createCriteria(Book.class)
                        .add(Restrictions.or(
                                Restrictions.ilike("bookTitle", keyword, MatchMode.ANYWHERE),
                                Restrictions.ilike("bookBriefContent", keyword, MatchMode.ANYWHERE))
                        )
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list();
        if(books == null || books.size() == 0)System.out.println("Books from fetchByContent is empty.");
        return books != null ? books : new LinkedList<>();
    }

    private List<Book> fetchByPublisherYear(Session session, String keyword) {
        try{
            int year = Integer.parseInt(keyword);
            return session.createCriteria(Book.class)
                    .add(Restrictions.eq("bookPublishYear", year))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
        }
        catch (Exception e){
            //e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private List<Book> fetchByClassificatioName(Session session, String keyword){
        List<Book> result =  session.createCriteria(Book.class)
                .createCriteria("bookClassification")
                .add(Restrictions.ilike("classificationName", keyword, MatchMode.ANYWHERE))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
        if(result == null || result.size() == 0)System.out.println("Books from fetchByClassificatioName is empty.");
        return result != null ? result : new LinkedList<>();
    }

    private List<Book> fetchByAuthorName(Session session, String keyword){
        List<Book> result =  session.createCriteria(Book.class)
                .createCriteria("bookAuthors")
                .add(Restrictions.ilike("authorName", keyword, MatchMode.ANYWHERE))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();

        if(result == null || result.size() == 0)System.out.println("Books from fetchByAuthorName is empty.");

        return result != null ? result : new LinkedList<>();
    }

    private List<Book> fetchByPublisherName(Session session, String keyword){
        List<Book> result =  session.createCriteria(Book.class)
                .createCriteria("bookPublisher")
                .add(Restrictions.ilike("publisherName", keyword, MatchMode.ANYWHERE))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
        if(result == null || result.size() == 0)System.out.println("Books from fetchByPublisherName is empty.");
        return result != null ? result : new LinkedList<>();
    }

    @Override
    public void clearAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        sessionHandler.runCUD(session -> {
            session.createQuery("delete from Book").executeUpdate();
            return true;
        });
    }
}
