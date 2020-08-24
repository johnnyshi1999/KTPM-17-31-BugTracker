package Business;

import Business.Strategies.TaskManageStrategy;
import DAO.IssueDAO;
import DTO.IssueDTO;
import Entities.Issue;

public class AdvancedTaskManageStrategy extends NormalTaskManageStrategy implements TaskManageStrategy {
    @Override
    public void deleteIssue(IssueDTO dto) {
        for (Issue issue : manager.getCurrentProject().getIssues()) {
            if (issue.getId().equals(dto.getId())) {
                IssueDAO dao = new IssueDAO();
                dao.delete(issue);
            }
        }
    }

    @Override
    public void editIssue(IssueDTO dto) {
        super.editIssue(dto);
    }

    @Override
    public void createIssue(IssueDTO dto) {
        super.editIssue(dto);
    }
}
