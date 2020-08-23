package Presentation.HomeWindow;

import Business.INotifyChange;
import Business.Notifier;
import Business.UserManager;
import Entities.User;
import Presentation.HomeWindow.Panes.HomeController;
import Presentation.HomeWindow.Panes.ProfileController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    HomeController homeController = null;
    ProfileController profileController = null;
    Stage stage;
    @FXML
    AnchorPane PaneParent;
    @FXML
    JFXButton loginButton;
    @FXML
    JFXButton homeButton;
    @FXML
    JFXButton profileButton;

    @FXML
    JFXButton registerButton;
    @FXML
    Text usernameText;
    @FXML
    MenuButton userMenuButton;

    @FXML
    MenuItem logoutMenuItem;


    ToggleGroup sidePaneToggleGroup = new ToggleGroup();

    public MainController(Stage primaryStage) {
        stage = primaryStage;
    }

    public void load() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow/main.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        
        stage.setTitle("BugTracker");
        stage.setScene(new Scene(root, 1280, 720));
        stage.setResizable(false);
        stage.show();
        UserManager.getManager().getNotifiers().add(new Notifier() {
            @Override
            public void notifyChange(INotifyChange t) {
                checkAccess();
            }
        });
        homeButton.fire();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameText.setText(UserManager.getManager().getLoggedInUserInfo().getUsername());
        logoutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserManager.getManager().removeLoggedInUser();
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showHomepane();
            }
        });


        profileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showProfilePane();
            }
        });

    }

    public void showHomepane() {
        if (homeController == null) {
            homeController = new HomeController(this);
        }
        homeController.load();

    }

    public void showProfilePane() {
        if (profileController == null) {
            profileController = new ProfileController(this);
        }
        profileController.load();
    }

    public void checkAccess() {
        if (UserManager.getManager().getLoggedInUserInfo() == null) {
            stage.close();
        }
    }

    public Pane getParentPane() {
        return PaneParent;
    }
}
