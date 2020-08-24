package Presentation.ProjectWindow;

import Business.UserManager;
import DTO.ProjectDTO;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProjectController implements Initializable {
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

    public EditProjectController(ProjectDTO dto) {
        this.dto = dto;
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass().getResource("/popup/project-input.fxml"));
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
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    editProjectDTO();
                    UserManager.getManager().editProject(dto);
                    stage.close();
                } catch (Exception e) {
                    errorText.setText(e.getMessage());
                }
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }

    private void editProjectDTO() throws Exception {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        if (title.equals("") ) {
            throw new Exception("Empty title");
        }

        dto.setTitle(title);
        dto.setDescription(description);
    }
}
