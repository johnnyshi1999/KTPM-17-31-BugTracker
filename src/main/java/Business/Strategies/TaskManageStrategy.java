package Business.Strategies;

import DTO.IssueDTO;
import Entities.Issue;

public interface TaskManageStrategy {
    public void deleteIssue(IssueDTO dto);

    public void editIssue(IssueDTO dto);

    public void createIssue(IssueDTO dto);
}
