package DTO;

import Entities.Issue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;

public class IssueDTO {
    private String id;
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty label;
    private StringProperty status;
//    private StringProperty assignee = new SimpleStringProperty("");
    private StringProperty dueDate = new SimpleStringProperty("");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    public void copyInfo(Issue issue) {
        id = issue.getId();
        setTitle(issue.getTitle());
        setDescription(issue.getDescription());
//        if (issue.)
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

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

//    public String getAssignee() {
//        return assignee.get();
//    }
//
//    public void setAssignee(String assignee) {
//        this.assignee.set(assignee);
//    }

    public String getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
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

    public StringProperty statusProperty() {
        return status;
    }

//    public StringProperty assigneeProperty() {
//        return assignee;
//    }

    public StringProperty dueDateProperty() {
        return dueDate;
    }
}
