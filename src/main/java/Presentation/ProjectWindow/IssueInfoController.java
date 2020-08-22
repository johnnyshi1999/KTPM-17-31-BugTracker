package Presentation.ProjectWindow;

import DTO.IssueDTO;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IssueInfoController implements Initializable {
    public boolean requestEdit = false;
    Stage stage;
    @FXML
    TextField titleTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    TextField statusTextField;
    @FXML
    TextField labelTextField;
    @FXML
    TextField assigneeTextField;
    @FXML
    TextField dueDateTextField;
    @FXML
    TextArea noteTextArea;
    @FXML
    JFXButton editButton;

    IssueDTO dto = null;

    public IssueInfoController(IssueDTO dto) {
        this.dto = dto;
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass()
                    .getResource("/ProjectWindow/issue-info.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleTextField.setText(dto.getTitle());
        descriptionTextArea.setText(dto.getDescription());
        statusTextField.setText(dto.getStatus().label);
        labelTextField.setText(dto.getLabel());

        assigneeTextField.setText(dto.getAssignee());
        dueDateTextField.setText(dto.getDueDate());
        noteTextArea.setText(dto.getNote());

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                requestEdit = true;
                stage.close();
            }
        });
    }
}
