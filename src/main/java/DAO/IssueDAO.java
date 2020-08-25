package DAO;

import Entities.Issue;
import hibernate.HibernateUtils;
import org.hibernate.Session;

public class IssueDAO implements DAOInterface<Issue>{
    @Override
    public void save(Issue issue) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.persist(issue);
            session.flush();
            session.getTransaction().commit();

            issue.fireNotifiers();
            issue.getProject().fireNotifiers();
            issue.getProject().fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(Issue issue) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(issue);
            session.flush();
            session.getTransaction().commit();

            issue.fireNotifiers();
            issue.getProject().fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    @Override
    public void delete(Issue issue) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.delete(issue);
            session.flush();
            session.getTransaction().commit();
//            issue.fireNotifiers();
            issue.getProject().fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
