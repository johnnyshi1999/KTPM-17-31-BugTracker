package DAO;

import Entities.User;
import hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO implements DAOInterface<User>{

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

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}