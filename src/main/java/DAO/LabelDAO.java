package DAO;

import Entities.Label;
import hibernate.HibernateUtils;
import org.hibernate.Session;

public class LabelDAO implements DAOInterface<Label> {
    @Override
    public void save(Label label) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.persist(label);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    @Override
    public void update(Label label) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(label);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Label label) {

    }
}
