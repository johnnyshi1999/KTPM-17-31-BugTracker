package Business;

import DAO.AssignmentDAO;
import DAO.IssueDAO;
import DAO.LabelDAO;
import DTO.IssueDTO;
import DTO.MemberDTO;
import DTO.ProjectDTO;
import Entities.*;
import javafx.beans.property.BooleanProperty;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProjectManager {
    private Project currentProject;
    private ProjectDTO dto;

    private TeamManager teamManager = new TeamManager(this);

    /**
     * Bind DTO to Project, add a notifier to project, that whenever project persists or update,
     * DTO will supdate
     * @param project
     * @param dto
     */
    public static void bindDtoToObject(Project project, ProjectDTO dto) {
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

    Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(ProjectDTO dto) {
        this.dto = dto;
        currentProject = UserManager.getProjectFromDTO(dto);
    }

    public List<IssueDTO> getProjectIssues() {
        List<IssueDTO> issueDTOS = new ArrayList<>();

        for (Issue issue : currentProject.getIssues()) {
            IssueDTO dto = new IssueDTO();
            dto.copyInfo(issue);
            IssueManager.bindDtoToObject(issue, dto);
            issueDTOS.add(dto);
        }

        return issueDTOS;
    }

    public List<String> getProjectLabels() {
        List<String> labels = new ArrayList<>();

        for (Label label : currentProject.getLabels()) {
            String labelName = label.getLabelName();
            labels.add(labelName);
        }
        return labels;
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
            User assignee = getTeamMember(dto.getAssignee());
            if (assignee != null) {
                Assignment assignment = new Assignment();
                assignment.setIssue(issue);
                assignment.setDev(getTeamMember(dto.getAssignee()));
                try {
                    assignment.setDeadline(dto.getDateFormat().parse(dto.getDueDate()));
                } catch (ParseException e) {
                    assignment.setDeadline(null);
                }
                assignment.setNote(dto.getNote());
                issue.setAssignment(assignment);
                assignment.setIssue(issue);
            }
        }

        currentProject.getIssues().add(issue);
        issue.setId(UUID.randomUUID().toString());
        IssueDAO dao = new IssueDAO();
        dao.save(issue);

        if (issue.getAssignment() != null) {
            new AssignmentDAO().save(issue.getAssignment());
        }
    }

    public boolean getAssignRight() {
        return UserManager.getManager().getAssignRightOnProject(currentProject);
    }

    public void createLabel(String labelStr) {
        Label label = new Label();
        label.setLabelName(labelStr);
        label.setProject(currentProject);
        new LabelDAO().save(label);
    }

    public void deleteIssue(IssueDTO dto) {

        for (Issue issue : currentProject.getIssues()) {
            if (issue.getId().equals(dto.getId())) {
                IssueDAO dao = new IssueDAO();
                dao.delete(issue);
            }
        }
    }

    public void updateIssue(IssueDTO dto) {
        boolean assignRight = UserManager.getManager().getAssignRightOnProject(currentProject);

        Issue issue = null;

        for (Issue i : currentProject.getIssues()) {
            if (i.getId().equals(dto.getId())) {
                issue = i;
                break;
            }
        }

        IssueManager.bindDtoToObject(issue, dto);
        issue.setProject(currentProject);
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setCreator(UserManager.getManager().getLoggedInUser());
        issue.setDateCreated(new Date());
        issue.setStatus(dto.getStatus().value);
        issue.setLabel(getLabel(dto.getLabel()));

        if (assignRight) {
            User assignee = getTeamMember(dto.getAssignee());
            if (assignee != null) {
                Assignment assignment = new Assignment();
                assignment.setDev(getTeamMember(dto.getAssignee()));
                try {
                    assignment.setDeadline(dto.getDateFormat().parse(dto.getDueDate()));
                } catch (ParseException e) {
                    assignment.setDeadline(null);
                }
                assignment.setNote(dto.getNote());
                assignment.setCreator(UserManager.getManager().getLoggedInUser());
                assignment.setIssue(issue);
                issue.setAssignment(assignment);
            }
        }
        IssueDAO dao = new IssueDAO();
        dao.update(issue);
//        if (issue.getAssignment() != null) {
//            new AssignmentDAO().update(issue.getAssignment());
//        }
    }

    public List<String> getMemberNameList() {
        List<String> members = new ArrayList<>();
        for (ProjectTeam projectTeam : currentProject.getTeam()) {
            members.add(projectTeam.getUser().getUsername());
        }
        return members;
    }

    public List<MemberDTO> getMemberList() {
        List<MemberDTO> memberDTOS = new ArrayList<>();

        for (ProjectTeam projectTeam : currentProject.getTeam()) {
            MemberDTO dto = new MemberDTO();
            dto.copyInfo(projectTeam);
            TeamManager.bindDtoToObject(projectTeam, dto);
            memberDTOS.add(dto);
        }

        return memberDTOS;
    }

    public void inviteUser(MemberDTO memberDTO) throws Exception {
        teamManager.addUserToTeam(memberDTO);
    }
}
