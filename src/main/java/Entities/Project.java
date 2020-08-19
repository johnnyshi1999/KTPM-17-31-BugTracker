package Entities;

import Business.Notifier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    private int id;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private Date dateCreated;
    private User manager;
    private Set<ProjectTeam> team = new HashSet<>();
    private Set<Issue> issues = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Set<ProjectTeam> getTeam() {
        return team;
    }

    public void setTeam(Set<ProjectTeam> team) {
        this.team = team;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    private List<Notifier<Project>> notifiers = new ArrayList<>();

    public List<Notifier<Project>> getNotifiers() {
        return notifiers;
    }

    public void fireNotifiers() {
        for (int i = 0; i < notifiers.size(); i++) {
            notifiers.get(i).notifyChange(this);
        }
    }
}
