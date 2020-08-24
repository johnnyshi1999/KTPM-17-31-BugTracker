package Business;

import Business.Strategies.TaskManageStrategy;
import DAO.IssueDAO;
import DTO.IssueDTO;
import Entities.Issue;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.util.Date;
import java.util.UUID;

public class NormalTaskManageStrategy implements TaskManageStrategy {
    ProjectManager manager;

    @Override
    public void deleteIssue(IssueDTO dto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Access Denied");
//                    alert.setContentText("You don't have Team Managing right\n" + "Contact your team manager to know more about this");
        DialogPane pane = alert.getDialogPane();
        pane.setContent(new Label("You don't have Task Managing right\n" + "Contact your team manager to know more about this\n"));
        pane.getStylesheets().add(
                getClass().getResource("/css/dialog.css").toExternalForm());
        alert.showAndWait();
    }

    @Override
    public void editIssue(IssueDTO dto) {
        Issue issue = null;

        for (Issue i : manager.getCurrentProject().getIssues()) {
            if (i.getId().equals(dto.getId())) {
                issue = i;
                break;
            }
        }

        IssueManager.bindDtoToObject(issue, dto);
        issue.setProject(manager.getCurrentProject());
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setCreator(UserManager.getManager().getLoggedInUser());
        issue.setDateCreated(new Date());
        issue.setStatus(dto.getStatus().value);
        issue.setLabel(manager.getLabel(dto.getLabel()));

        IssueDAO dao = new IssueDAO();
        dao.update(issue);
    }

    @Override
    public void createIssue(IssueDTO dto) {
        Issue issue = new Issue();
        IssueManager.bindDtoToObject(issue, dto);
        issue.setProject(manager.getCurrentProject());
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setCreator(UserManager.getManager().getLoggedInUser());
        issue.setDateCreated(new Date());
        issue.setStatus(0);
        issue.setLabel(manager.getLabel(dto.getLabel()));

        manager.getCurrentProject().getIssues().add(issue);
        issue.setId(UUID.randomUUID().toString());
        IssueDAO dao = new IssueDAO();
        dao.save(issue);
    }
}
