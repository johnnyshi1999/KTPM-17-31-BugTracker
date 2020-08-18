package Entities;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Date;

public class Issue implements Serializable {
    private String id;
    private Project project;
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private User creator;
    private Date dateCreated;
    private IntegerProperty status = new SimpleIntegerProperty();
    private ObjectProperty<Label> label = new SimpleObjectProperty<Label>();

    public Issue(Project project) {
        this.project = project;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public Label getLabel() {
        return label.get();
    }

    public void setLabel(Label label) {
        this.label.set(label);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public ObjectProperty<Label> labelProperty() {
        return label;
    }
}
