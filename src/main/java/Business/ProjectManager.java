package Business;

import DAO.IssueDAO;
import DTO.IssueDTO;
import DTO.ProjectDTO;
import Entities.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectManager {
    private Project currentProject;
    private ProjectDTO dto;

    /**
     * Bind DTO to Project, add a notifier to project, that whenever project persists or update,
     * DTO will supdate
     * @param project
     * @param dto
     */
    public static void bindDtoToObject(Project project, ProjectDTO dto) {
//        dto.copyInfo(project);

//        Notifier notifier = new Notifier() {
//            @Override
//            public void notifyChange(Project project) {
//                dto.copyInfo(project);
//            }
//        };
        Notifier notifier = new Notifier() {
            @Override
            public void notifyChange(INotifyChange t) {
                dto.copyInfo((Project)t);
            }
        };
        project.getNotifiers().add(notifier);
    }

    public ProjectManager(ProjectDTO dto) {
        setCurrentProject(dto);
    }

    public void setCurrentProject(ProjectDTO dto) {
        this.dto = dto;
        currentProject = UserManager.getProjectFromDTO(dto);
    }

    public List<IssueDTO> getProjectIssues() {
        List<IssueDTO> issueDTOS = new ArrayList<>();

        for (Issue issue : currentProject.getIssues()) {
            IssueDTO dto = new IssueDTO();
            IssueManager.bindDtoToObject(issue, dto);
        }

        return issueDTOS;
    }

    public ProjectDTO getDto() {
        return dto;
    }

    private Label getLabel(String labelName) {

        for (Label label : currentProject.getLabels()) {
            if (label.getLabelName().equals(labelName)) {
                return label;
            }
        }
        return null;
    }

    private User getTeamMember(String username) {

        for (ProjectTeam member : currentProject.getTeam()) {
            if (member.getUser().getUsername().equals(username)) {
                return member.getUser();
            }
        }
        return null;
    }

    public void createIssue(IssueDTO dto) {
        boolean assignRight = UserManager.getManager().getAssignRightOnProject(currentProject);

        Issue issue = new Issue();
        IssueManager.bindDtoToObject(issue, dto);
        issue.setProject(currentProject);
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setCreator(UserManager.getManager().getLoggedInUser());
        issue.setDateCreated(new Date());
        issue.setStatus(0);
        issue.setLabel(getLabel(dto.getLabel()));

        if (assignRight) {
            Assignment assignment = new Assignment();
            assignment.setDev(getTeamMember(dto.getAssignee()));
            try {
                assignment.setDeadline(dto.getDateFormat().parse(dto.getDueDate()));
            } catch (ParseException e) {
                assignment.setDeadline(null);
            }
        }

        currentProject.getIssues().add(issue);
        IssueDAO dao = new IssueDAO();
        dao.save(issue);
    }
}
