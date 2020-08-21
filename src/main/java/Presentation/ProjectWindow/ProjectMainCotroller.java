package Presentation.ProjectWindow;

import Business.ProjectManager;
import DTO.ProjectDTO;
import Presentation.ProjectWindow.Pane.AddIssueController;
import Presentation.ProjectWindow.Pane.ProjectHomeController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectMainCotroller implements Initializable {
    public ProjectManager manager;
    ProjectHomeController homeController = null;
    AddIssueController addIssueController = null;
    Stage stage;
    @FXML
    AnchorPane parentPane;
    @FXML
    JFXButton homeButton;
    @FXML
    JFXButton addIssueButton;
    @FXML
    Text titleText;
    @FXML
    VBox sidePaneVBox;
    public ProjectMainCotroller(ProjectDTO dto) {
        manager = new ProjectManager(dto);
    }

    public AnchorPane getParentPane() {
        return parentPane;
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
//
                showHomePane();
//                setHighLightPaneSelection(homeButton);
            }
        });
        homeButton.fire();

        addIssueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAddIssuePane();
//                setHighLightPaneSelection(addIssueButton);

            }
        });
    }

    private void showHomePane() {
        if (homeController == null) {
            homeController = new ProjectHomeController(parentPane, manager);
        }
        homeController.load();
        homeButton.requestFocus();
    }

    private void showAddIssuePane() {
        if (addIssueController == null) {
            addIssueController = new AddIssueController(this);
        }
        addIssueController.load();
    }

    public void returnToHome() {
        if (addIssueController.getDto() != null) {
            homeController.getIssues().add(addIssueController.getDto());
        }
        homeButton.fire();
        homeButton.requestFocus();
    }

    private void setHighLightPaneSelection(JFXButton button) {
        List<Node> nodeList = sidePaneVBox.getChildren();
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setStyle("-fx-background-color: null;");
        }
        button.setStyle("-fx-background-color: #788fe2");

    }
}
