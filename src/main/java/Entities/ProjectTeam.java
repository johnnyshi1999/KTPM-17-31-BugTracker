package Entities;

import Business.Notifer.INotifyChange;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.Serializable;
import java.util.Date;

public class ProjectTeam extends INotifyChange implements Serializable {
    private Project project;
    private User user;
    private Date dateJoined;
    private BooleanProperty assignRight = new SimpleBooleanProperty();
    private BooleanProperty inviteRight = new SimpleBooleanProperty();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public boolean isAssignRight() {
        return assignRight.get();
    }

    public void setAssignRight(boolean assignRight) {
        this.assignRight.set(assignRight);
    }

    public boolean isInviteRight() {
        return inviteRight.get();
    }

    public void setInviteRight(boolean inviteRight) {
        this.inviteRight.set(inviteRight);
    }

    public BooleanProperty assignRightProperty() {
        return assignRight;
    }

    public BooleanProperty inviteRightProperty() {
        return inviteRight;
    }
}
