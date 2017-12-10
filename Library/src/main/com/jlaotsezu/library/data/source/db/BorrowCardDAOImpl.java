package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.BorrowCardDAO;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BorrowCardDAOImpl implements BorrowCardDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public List<BorrowCard> findByUserId(int userId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(BorrowCard.class)
                        .add(Restrictions.eq("user.userId", userId))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());
    }
    @Override
    public List<BorrowCard> fetchAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                session.createCriteria(BorrowCard.class)
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());
    }

    @Override
    public BorrowCard fetchById(Integer id) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->
                (BorrowCard)
                 session.createCriteria(BorrowCard.class)
                        .add(Restrictions.eq("borrowCardId", id))
                        .uniqueResult());
    }

    @Override
    public boolean saveAll(List<BorrowCard> borrowCards) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            boolean success = true;
            for(int i =0; i < borrowCards.size(); i++){
                BorrowCard borrowCard = borrowCards.get(i);
                session.save(borrowCard);
                if(borrowCard.getBorrowCardId() <= 0){
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
    public boolean save(BorrowCard borrowCard) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> { 
                session.save(borrowCard);
                return borrowCard.getBorrowCardId() > 0;
        });
    }
    @Override
    public boolean updateAll(List<BorrowCard> borrowCards) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            boolean success = true;
            for(int i =0; i < borrowCards.size(); i++){
                BorrowCard borrowCard = borrowCards.get(i);
                session.update(borrowCard);
                if(borrowCard.getBorrowCardId() <= 0){
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
    public boolean update(BorrowCard borrowCard) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
                session.update(borrowCard);
                return borrowCard.getBorrowCardId() > 0;
        });
    }
    @Override
    public void clearAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        sessionHandler.runCUD(session -> {
            session.createQuery("delete from BorrowCard ").executeUpdate();
            return true;
        });
    }

    @Override
    public List<BorrowCard> findByKeyword(String keyword) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session ->  {
            Set<BorrowCard> cards = new LinkedHashSet<>();

            cards.addAll(findByUser(session, keyword));
            cards.addAll(findByBorrowCardId(session, keyword));

            return new LinkedList<>(cards);
        });
    }

    private List<BorrowCard> findByBorrowCardId(Session session, String keyword){
        try {
            int borrowCardId = Integer.parseInt(keyword);
            return session.createCriteria(BorrowCard.class)
                    .add(Restrictions.eq("borrowCardId", borrowCardId))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
        }
        catch (Exception e){
            return new LinkedList<>();
        }
    }
    private List<BorrowCard> findByUser(Session session, String keyword){
        Criteria criteria;
        try {
            int userId = Integer.parseInt(keyword);
            criteria = session.createCriteria(BorrowCard.class)
                    .createCriteria("user")
                    .add(Restrictions.or(
                            Restrictions.ilike("fullName", keyword, MatchMode.ANYWHERE),
                            Restrictions.ilike("userName", keyword, MatchMode.ANYWHERE),
                            Restrictions.eq("userId", userId)
                    ));
        }
        catch (Exception e){
            criteria = session.createCriteria(BorrowCard.class)
                    .createCriteria("user")
                    .add(Restrictions.or(
                            Restrictions.ilike("userName", keyword, MatchMode.ANYWHERE),
                            Restrictions.ilike("fullName", keyword, MatchMode.ANYWHERE)
                            )
                    );
        }
        return criteria
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
