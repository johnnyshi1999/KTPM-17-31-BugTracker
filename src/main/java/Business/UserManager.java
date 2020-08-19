package Business;

import DAO.ProjectDAO;
import DAO.ProjectTeamDAO;
import DAO.UserDAO;
import DTO.ProjectDTO;
import Entities.Project;
import Entities.ProjectTeam;
import Entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager {

    private static UserManager manager = null;
    private User loggedInUser = null;

    public static UserManager getManager() {
        if (manager == null) {
            manager = new UserManager();
        }
        return manager;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String username, String password) {
        loggedInUser = new UserDAO().getLoggedInUser(username, password);

    }

    public void createProject(ProjectDTO dto) throws Exception {
        Date date = new Date();
        Project project = new Project();
        project.setName(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setManager(loggedInUser);
        project.setDateCreated(date);

        ProjectDAO dao = new ProjectDAO();
        dao.save(project);

        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProject(project);
        projectTeam.setUser(loggedInUser);
        projectTeam.setDateJoined(date);
        projectTeam.setAssignRight(true);
        projectTeam.setInviteRight(true);

        project.getTeam().add(projectTeam);
        loggedInUser.getProjects().add(projectTeam);

        new ProjectTeamDAO().save(projectTeam);
        ProjectManager.bindProjectToDTO(project, dto);
    }

    public List<ProjectDTO> getUserProjects() {
        List<ProjectDTO> result = new ArrayList<>();
        List<ProjectTeam> projects = new ArrayList<ProjectTeam>(loggedInUser.getProjects());
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i).getProject();
            ProjectDTO dto = new ProjectDTO();
            ProjectManager.bindProjectToDTO(project, dto);
//            dto.copyInfo(project);
//
//            Notifier<Project> notifier = new Notifier<Project>() {
//                @Override
//                public void notifyChange(Project project) {
//                    dto.copyInfo(project);
//                }
//            };
//            project.getNotifiers().add(notifier);

            result.add(dto);
        }
        return result;
    }
}
