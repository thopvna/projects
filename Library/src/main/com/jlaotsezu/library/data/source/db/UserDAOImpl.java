package main.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.supports.SessionHandler;
import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public boolean save(User user) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.save(user);
            return user.getUserId() > 0;
        });
    }
    @Override
    public boolean update(User user) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runCUD(session -> {
            session.update(user);
            return user.getUserId() > 0;
        });
    }

    @Override
    public User fetchById(int userId) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> (User)
                session.createCriteria(User.class)
                        .add(Restrictions.eq("userId", userId))
                        .uniqueResult()
        );
    }

    @Override
    public User fetchByUserName(String userName) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch(session -> (User)
                session.createCriteria(User.class)
                        .add(Restrictions.eq("userName", userName))
                        .uniqueResult()
        );
    }

    @Override
    public void clearAll() {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        sessionHandler.runCUD(session -> {
            session.createQuery("delete from User").executeUpdate();
            return true;
        });
    }

    @Override
    public List<User> findByKeyword(String keyword) {
        SessionHandler sessionHandler = new SessionHandler(sessionFactory.getCurrentSession());
        return sessionHandler.runFetch( session -> {
            Criteria criteria;
            try {
                int userId = Integer.parseInt(keyword);
                criteria = session.createCriteria(User.class)
                        .add(Restrictions.or(
                                Restrictions.ilike("userName", keyword, MatchMode.ANYWHERE),
                                Restrictions.eq("userId", userId),
                                Restrictions.ilike("fullName", keyword, MatchMode.ANYWHERE)
                        ));
            }
            catch (Exception ignored){
                criteria = session.createCriteria(User.class)
                        .add(Restrictions.or(
                                Restrictions.ilike("userName",keyword, MatchMode.ANYWHERE),
                                Restrictions.ilike("fullName", keyword, MatchMode.ANYWHERE)
                        ));
            }
            return criteria
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
        });
    }
}
