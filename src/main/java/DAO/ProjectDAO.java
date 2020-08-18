package DAO;

import Entities.Project;
import Entities.User;
import hibernate.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProjectDAO implements DAOInterface<Project> {
    ObservableList<Project> projectList = null;
    public ObservableList<Project> getProjectList(User user) {
        if (projectList == null) {
            Session session = HibernateUtils.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createNativeQuery("call SP_GetUserProjects(:user_id)")
                    .addEntity(User.class)
                    .setParameter("user_id", user.getId());
            List<Project> result = query.getResultList();
            session.getTransaction().commit();

            projectList = FXCollections.observableArrayList(result);
        }
        return projectList;
    }

    @Override
    public void save(Project project) {

    }

    @Override
    public void update(Project project) {

    }

    @Override
    public void delete(Project project) {

    }
}
