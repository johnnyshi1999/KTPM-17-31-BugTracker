package DAO;

import Business.Notifer.INotifyChange;
import Entities.User;
import hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO extends INotifyChange implements DAOInterface<User>{

    public User getLoggedInUser(String username, String password) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        User user = null;
        try {
            session.getTransaction().begin();
            Query query = session.createNativeQuery("select * from user where user.username = :username and user.password = :password")
                    .addEntity(User.class)
                    .setParameter("username", username)
                    .setParameter("password", password);
            user = (User) query.getSingleResult();


            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return user;
    }


    @Override
    public void save(User user) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.save(user);
            session.flush();
            session.getTransaction().commit();
            user.fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(User user) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
            user.fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void delete(User user) {

    }

    public User findByUsername(String username) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        User user = null;
        try {
            session.getTransaction().begin();
            Query query = session.createNativeQuery("select * from user where user.username = :username")
                    .addEntity(User.class)
                    .setParameter("username", username);
            user = (User) query.getSingleResult();


            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return user;
    }

    public boolean validateUsername(String username) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            Query query = session.createNativeQuery("select * from user where user.username = :username")
                    .addEntity(User.class)
                    .setParameter("username", username);
            List<User> userList = query.getResultList();

            session.getTransaction().commit();

            if (userList.size() == 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return false;
    }
}
