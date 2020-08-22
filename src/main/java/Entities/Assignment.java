package Entities;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.Date;

public class Assignment implements Serializable {
    private String id;
    private Issue issue;
    private User dev;
    private User creator;
    private ObjectProperty<Date> deadline = new SimpleObjectProperty<Date>();
    private StringProperty note = new SimpleStringProperty();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public User getDev() {
        return dev;
    }

    public void setDev(User dev) {
        this.dev = dev;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDeadline() {
        return deadline.get();
    }

    public void setDeadline(Date deadline) {
        this.deadline.set(deadline);
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public ObjectProperty<Date> deadlineProperty() {
        return deadline;
    }

    public StringProperty noteProperty() {
        return note;
    }
}
