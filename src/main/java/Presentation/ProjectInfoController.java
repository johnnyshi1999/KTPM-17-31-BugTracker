package Presentation;

import Business.UserManager;
import DTO.ProjectDTO;
import Entities.Project;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectInfoController implements Initializable {
    Stage stage;
    @FXML
    TextField titleTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    Text errorText;
    @FXML
    JFXButton confirmButton;
    @FXML
    JFXButton cancelButton;

    ProjectDTO dto;

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass().getResource("/project-info.fxml"));
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
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dto = createProjectDTO();
                    UserManager.getManager().createProject(dto);
                    stage.close();
                } catch (Exception e) {
                    dto = null;
                    errorText.setText(e.getMessage());
                }
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dto = null;
                stage.close();
            }
        });
    }

    private ProjectDTO createProjectDTO() throws Exception {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        if (title.equals("") ) {
            throw new Exception("Empty title");
        }
        ProjectDTO dto = new ProjectDTO();
        dto.setTitle(title);
        dto.setDescription(description);
        return dto;
    }

    public ProjectDTO getDTO() {
        return dto;
    }
}
