package Presentation.ProjectWindow;

import DTO.ProjectDTO;
import Entities.Project;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectMainCotroller implements Initializable {
    ProjectDTO projectDTO;
    Stage stage;
    public ProjectMainCotroller(ProjectDTO dto) {
        projectDTO = dto;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass().getResource("/project-info.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.NONE);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
