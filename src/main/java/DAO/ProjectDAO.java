package DAO;

import Business.Notifier;
import Entities.Project;
import Entities.User;
import hibernate.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements DAOInterface<Project> {

    ObservableList<Project> projectList = null;

    public ObservableList<Project> getProjectList(User user) {
        if (projectList == null) {
            Session session = HibernateUtils.getSessionFactory().getCurrentSession();
            try {
                session.beginTransaction();
                Query query = session.createNativeQuery("call SP_GetUserProjects(:user_id)")
                        .addEntity(User.class)
                        .setParameter("user_id", user.getId());
                List<Project> result = query.getResultList();
                session.getTransaction().commit();
                projectList = FXCollections.observableArrayList(result);

            } catch (Exception e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }


        }
        return projectList;
    }

    @Override
    public void save(Project project) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.persist(project);
            session.flush();
            session.getTransaction().commit();
            project.fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(Project project) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(project);
            session.flush();
            session.getTransaction().commit();
            project.fireNotifiers();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    @Override
    public void delete(Project project) {

    }
}
