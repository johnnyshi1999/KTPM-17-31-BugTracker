package DTO;

import Business.Notifier;
import Entities.Project;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectDTO {
    private int id;
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty dateCreated = new SimpleStringProperty();
    private IntegerProperty openIssues = new SimpleIntegerProperty();
    private IntegerProperty members = new SimpleIntegerProperty();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    public void copyInfo(Project project) {
        id = project.getId();
        title.set(project.getName());
        description.set(project.getDescription());
        dateCreated.set(dateFormat.format(project.getDateCreated()));
        openIssues.set(project.getIssues().size());
        members.set(project.getTeam().size());


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

    public String getDateCreated() {
        return dateCreated.get();
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    public int getOpenIssues() {
        return openIssues.get();
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues.set(openIssues);
    }

    public int getMembers() {
        return members.get();
    }

    public void setMembers(int members) {
        this.members.set(members);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public IntegerProperty openIssuesProperty() {
        return openIssues;
    }

    public IntegerProperty membersProperty() {
        return members;
    }
}
