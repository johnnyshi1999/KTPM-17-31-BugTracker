package Business;

import Business.Notifer.INotifyChange;
import Business.Notifer.Notifier;
import DAO.ProjectDAO;
import DAO.ProjectTeamDAO;
import DAO.UserDAO;
import DTO.ProjectDTO;
import DTO.UserDTO;
import Entities.Project;
import Entities.ProjectTeam;
import Entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager extends INotifyChange {

    private static UserManager manager = null;
    private User loggedInUser = null;
    private UserDTO dto = null;
    private static List<ProjectTeam> projects = null;

    public static void bindDtoToObject(User user, UserDTO dto) {
        Notifier notifier = new Notifier() {
            @Override
            public void notifyChange(INotifyChange t) {
                dto.copyInfo(user);
            }
        };
        user.getNotifiers().add(notifier);
    }

    public static UserManager getManager() {
        if (manager == null) {
            manager = new UserManager();
        }
        return manager;
    }

    User findUserByUsername(String username) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsername(username);
        return user;
    }

    User getLoggedInUser() {
        return loggedInUser;
    }

    public UserDTO getLoggedInUserInfo() {
        return dto;
    }

    public boolean setLoggedInUser(String username, String password) {

        loggedInUser = new UserDAO().getLoggedInUser(username, password);
        if (loggedInUser != null) {
            dto = new UserDTO();
            dto.copyInfo(loggedInUser);
            bindDtoToObject(loggedInUser, dto);

            fireNotifiers();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setLoggedInUser(User user) {
        loggedInUser = user;
        if (loggedInUser != null) {
            dto = new UserDTO();
            dto.copyInfo(loggedInUser);
            bindDtoToObject(loggedInUser, dto);

            fireNotifiers();
            return true;
        }
        else {
            return false;
        }
    }

    public void removeLoggedInUser() {
        loggedInUser = null;
        dto = null;
        fireNotifiers();
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
        ProjectManager.bindDtoToObject(project, dto);
    }

    public List<ProjectDTO> getUserProjects() {
        List<ProjectDTO> result = new ArrayList<>();
        if (projects == null) {
            projects = new ArrayList<ProjectTeam>(loggedInUser.getProjects());
        }
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i).getProject();
            ProjectDTO dto = new ProjectDTO();
            dto.copyInfo(project);
            ProjectManager.bindDtoToObject(project, dto);
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

    public static Project getProjectFromDTO(ProjectDTO dto) {
        Project project = null;
        if (projects != null) {
            for (int i = 0; i < projects.size(); i++) {
                if (projects.get(i).getProject().getId() == dto.getId()) {
                    project = projects.get(i).getProject();
                    return project;
                }
            }
        }
        return project;
    }

    public boolean getAssignRightOnProject(Project project) {
        if (projects != null) {
            for (int i = 0; i < projects.size(); i++) {
                if (projects.get(i).getProject().getId() == project.getId()
                    && projects.get(i).getUser().getId() == loggedInUser.getId()) {
                    return projects.get(i).isAssignRight();
                }
            }
        }
        return false;
    }

    public void registerUser(UserDTO userDTO) throws Exception {
        UserDAO userDAO = new UserDAO();
        boolean check = userDAO.validateUsername(userDTO.getUsername());

        if (check == false) {
            throw new Exception("Username is taken, please choose another one");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        userDAO.save(user);
        bindDtoToObject(user, userDTO);
        setLoggedInUser(user);
    }

    public boolean getInviteRightOnProject(Project currentProject) {
        for (ProjectTeam projectTeam : loggedInUser.getProjects()) {
            if (projectTeam.getProject().getId() == currentProject.getId()) {
                return projectTeam.isInviteRight();
            }
        }
        return false;
    }

    public void editUserInfo() {
        loggedInUser.setPassword(dto.getPassword());
        loggedInUser.setEmail(dto.getEmail());

        new UserDAO().update(loggedInUser);
    }

    public void editProject(ProjectDTO dto) {
        for (ProjectTeam projectTeam : loggedInUser.getProjects()) {
            Project project = projectTeam.getProject();
            if (project.getId() == dto.getId()) {
                project.setName(dto.getTitle());
                project.setDescription(dto.getDescription());

                new ProjectDAO().update(project);
            }
        }
    }
}
