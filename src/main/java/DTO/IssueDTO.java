package DTO;

import Business.SharedPreference.IssueStatus;
import Entities.Issue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;

public class IssueDTO {
    private String id;
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty label = new SimpleStringProperty("");
    private ObjectProperty<IssueStatus> status = new SimpleObjectProperty<IssueStatus>();
    private StringProperty assignee = new SimpleStringProperty("");
    private StringProperty dueDate = new SimpleStringProperty("");
    private StringProperty note = new SimpleStringProperty("");

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void copyInfo(Issue issue) {
        id = issue.getId();
        setTitle(issue.getTitle());
        setDescription(issue.getDescription());
        if (issue.getLabel() != null) {
            setLabel(issue.getLabel().getLabelName());
        }
        else {
            setLabel("");
        }
        for (IssueStatus status : IssueStatus.values()) {
            if (status.value == issue.getStatus()) {
                setStatus(status);
            }
        }
        if (issue.getAssignment() != null) {
            setAssignee(issue.getAssignment().getDev().getUsername());
            setDueDate(dateFormat.format(issue.getAssignment().getDeadline()));
        }
        else {
            setAssignee("");
            setDueDate("");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public IssueStatus getStatus() {
        return status.get();
    }

    public void setStatus(IssueStatus status) {
        this.status.set(status);
    }

    public String getAssignee() {
        return assignee.get();
    }

    public void setAssignee(String assignee) {
        this.assignee.set(assignee);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty labelProperty() {
        return label;
    }

    public ObjectProperty<IssueStatus> statusProperty() {
        return status;
    }

    public StringProperty assigneeProperty() {
        return assignee;
    }

    public StringProperty dueDateProperty() {
        return dueDate;
    }

    public StringProperty noteProperty() {
        return note;
    }
}
