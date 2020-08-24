package Presentation.ProjectWindow;

import DTO.ProjectDTO;
import com.jfoenix.controls.JFXButton;
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

public class ProjectInfoController implements Initializable {
    Stage stage;
    ProjectDTO dto;

    @FXML
    TextField titleTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    Text errorText;

    public ProjectInfoController(ProjectDTO dto) {
        this.dto = dto;
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass()
                    .getResource("/popup/project-info.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            stage = new Stage();
            stage.setTitle("Project Information");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.NONE);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleTextField.setText(dto.getTitle());
        descriptionTextArea.setText(dto.getDescription());
    }
}
