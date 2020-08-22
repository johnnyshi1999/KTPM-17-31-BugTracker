package Business;

import DAO.ProjectDAO;
import DAO.ProjectTeamDAO;
import DTO.MemberDTO;
import Entities.ProjectTeam;
import Entities.User;

import java.util.Date;

public class TeamManager {
    ProjectManager projectManager;

    public TeamManager(ProjectManager manager) {
        projectManager = manager;
    }
    public static void bindDtoToObject(ProjectTeam projectTeam, MemberDTO dto) {
        Notifier notifier = new Notifier() {
            @Override
            public void notifyChange(INotifyChange t) {
                dto.copyInfo(projectTeam);
            }
        };
        projectTeam.getNotifiers().add(notifier);
    }

    public void addUserToTeam(MemberDTO dto) throws Exception {

        User user = UserManager.getManager().findUserByUsername(dto.getUsername());
        if (user == null) {
            throw new Exception("Username does not exists");
        }

        for (ProjectTeam pt : projectManager.getCurrentProject().getTeam()) {
            if (pt.getUser().getId() == user.getId()) {
                throw new Exception("User has already joined");
            }
        }

        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProject(projectManager.getCurrentProject());
        projectTeam.setUser(user);
        projectTeam.setDateJoined(new Date());
        projectTeam.setAssignRight(dto.isAssignRight());
        projectTeam.setInviteRight(dto.isInviteRight());

        projectManager.getCurrentProject().getTeam().add(projectTeam);
        new ProjectTeamDAO().save(projectTeam);
    }
}
