package Presentation.ProjectWindow;

import Business.ProjectManager;
import DTO.ProjectDTO;
import Entities.Project;
import Presentation.ProjectWindow.Pane.ProjectHomeController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectMainCotroller implements Initializable {
    ProjectManager manager;
    ProjectHomeController homeController = null;
    Stage stage;
    @FXML
    AnchorPane PaneParent;
    @FXML
    JFXButton homeButton;
    @FXML
    JFXButton addIssueButton;
    @FXML
    Text titleText;
    public ProjectMainCotroller(ProjectDTO dto) {
        manager = new ProjectManager(dto);
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass().getResource("/ProjectWindow/main.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleText.textProperty().bind(manager.getDto().titleProperty());
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showHomePane();
            }
        });
        homeButton.fire();
    }

    private void showHomePane() {
        if (homeController == null) {
            homeController = new ProjectHomeController(PaneParent, manager);
        }
        homeController.load();
    }


}
