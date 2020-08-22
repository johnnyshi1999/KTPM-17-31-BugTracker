package DTO;

import Business.SharedPreference;
import Entities.ProjectTeam;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberDTO {
    private StringProperty username = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private BooleanProperty assignRight = new SimpleBooleanProperty();
    private BooleanProperty inviteRight = new SimpleBooleanProperty();
    private StringProperty dateJoined = new SimpleStringProperty();

    public void copyInfo(ProjectTeam projectTeam) {
        setUsername(projectTeam.getUser().getUsername());
        setEmail(projectTeam.getUser().getEmail());
        setAssignRight(projectTeam.isAssignRight());
        setInviteRight(projectTeam.isInviteRight());
        setDateJoined(SharedPreference.getDateFormat().format(projectTeam.getDateJoined()));
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean isAssignRight() {
        return assignRight.get();
    }

    public void setAssignRight(boolean asignRight) {
        this.assignRight.set(asignRight);
    }

    public boolean isInviteRight() {
        return inviteRight.get();
    }

    public void setInviteRight(boolean inviteRight) {
        this.inviteRight.set(inviteRight);
    }

    public String getDateJoined() {
        return dateJoined.get();
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined.set(dateJoined);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public BooleanProperty assignRightProperty() {
        return assignRight;
    }

    public BooleanProperty inviteRightProperty() {
        return inviteRight;
    }

    public StringProperty dateJoinedProperty() {
        return dateJoined;
    }
}
