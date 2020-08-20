package Presentation.HomeWindow;

import Business.UserManager;
import Entities.User;
import Presentation.HomeWindow.Panes.HomeController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    HomeController homeController = null;
    @FXML
    AnchorPane PaneParent;
    @FXML
    JFXButton loginButton;
    @FXML
    JFXButton homeButton;

    @FXML
    JFXButton registerButton;

    @FXML
    MenuButton userMenuButton;
    @FXML
    HBox notLoggedInHBox;
    @FXML
    HBox loggedInHBox;
    @FXML
    Text usernameText;
    @FXML
    MenuItem logoutMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLoggedInUser(null);
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showHomepane();
            }
        });
        homeButton.fire();

    }

    public void setLoggedInUser(User user) {
        //TEST
        UserManager.getManager().setLoggedInUser("user1", "user");
        homeButton.fire();
    }

    public void showHomepane() {
        if (homeController == null) {
            homeController = new HomeController(PaneParent);
        }
        homeController.load();

    }
}
