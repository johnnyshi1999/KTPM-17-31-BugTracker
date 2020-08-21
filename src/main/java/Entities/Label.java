package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Label implements Serializable {
    private int id;
    private Project project;
    private StringProperty labelName = new SimpleStringProperty();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getLabelName() {
        return labelName.get();
    }

    public void setLabelName(String labelName) {
        this.labelName.set(labelName);
    }
}
