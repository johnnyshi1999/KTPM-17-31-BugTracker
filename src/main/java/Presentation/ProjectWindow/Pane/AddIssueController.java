package Presentation.ProjectWindow.Pane;

import Business.ProjectManager;
import Presentation.PaneController.PaneController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddIssueController extends PaneController {
    ProjectManager manager;
    @FXML
    TextField titleTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    JFXComboBox<String> labelComboBox;
    @FXML
    JFXComboBox<String> statusComboBox;
    @FXML
    ComboBox<String> assigneeComboBox;
    @FXML
    JFXDatePicker datePicker;
    @FXML
    JFXTimePicker timePicker;
    @FXML
    TextArea noteTextArea;
    public AddIssueController(Pane parent, ProjectManager manager) {
        super(parent, "/ProjectWindow/issue-input.fxml");
        this.manager = manager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
