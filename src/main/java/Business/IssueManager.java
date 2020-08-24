package Business;

import Business.Notifer.INotifyChange;
import Business.Notifer.Notifier;
import DTO.IssueDTO;
import Entities.Issue;

public class IssueManager {
    public static void bindDtoToObject(Issue issue, IssueDTO dto) {
//        dto.copyInfo(issue);
        Notifier notifier = new Notifier() {
            @Override
            public void notifyChange(INotifyChange t) {
                dto.copyInfo((Issue)t);
            }
        };
        issue.getNotifiers().add(notifier);
    }
}
