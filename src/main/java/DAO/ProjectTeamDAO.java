package DAO;

import Entities.Project;
import Entities.ProjectTeam;
import hibernate.HibernateUtils;
import org.hibernate.Session;

public class ProjectTeamDAO implements DAOInterface<ProjectTeam>{
    @Override
    public void save(ProjectTeam projectTeam) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.persist(projectTeam);
            session.flush();
            session.getTransaction().commit();
            projectTeam.getProject().fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(ProjectTeam projectTeam) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(projectTeam);
            session.flush();
            session.getTransaction().commit();
            projectTeam.getProject().fireNotifiers();
            projectTeam.getUser().fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    @Override
    public void delete(ProjectTeam projectTeam) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.delete(projectTeam);
            session.flush();
            session.getTransaction().commit();
            projectTeam.getProject().fireNotifiers();
            projectTeam.getUser().fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

}
