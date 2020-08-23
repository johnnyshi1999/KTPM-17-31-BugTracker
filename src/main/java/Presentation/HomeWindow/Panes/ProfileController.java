package Presentation.HomeWindow.Panes;

import Business.SharedPreference;
import Business.UserManager;
import DTO.UserDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.HomeWindow.ChangePasswordController;
import Presentation.HomeWindow.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends PaneController<MainController> implements Initializable {
    String oldEmail;
    @FXML
    TextField usernameText;
    @FXML
    TextField emailTextField;
    @FXML
    JFXButton changePasswordButton;
    @FXML
    JFXToggleButton changeEmailButton;
    @FXML
    Text errorText;
    @FXML
    HBox editEmailHBox;
    @FXML
    JFXButton saveEmailButton;
    @FXML
    JFXButton cancelEmailButton;

    public ProfileController(MainController mainController) {
        super(mainController.getParentPane(), "/MainWindow/profile.fxml");
        mediator = mainController;
        parent = mainController.getParentPane();


    }

    @Override
    public void checkAccess() {
//        if (UserManager.getManager().getLoggedInUserInfo() == null) {
//            parent.getChildren().remove(container);
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChangePasswordController controller = new ChangePasswordController();
                controller.load();
            }
        });
        changeEmailButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldvalue, Boolean newvalue) {
                if (newvalue == true) {
                    oldEmail = new String(UserManager.getManager().getLoggedInUserInfo().getEmail());
                    editEmailHBox.setVisible(true);
                    emailTextField.setEditable(true);
                    changeEmailButton.setVisible(false);
                } else {
                    emailTextField.setEditable(false);
                    changeEmailButton.setVisible(true);
                    editEmailHBox.setVisible(false);
                    errorText.setText("");
                }
            }
        });

        saveEmailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String email = getEmailInput();

                    UserDTO dto = UserManager.getManager().getLoggedInUserInfo();
                    dto.setEmail(email);
                    UserManager.getManager().editUserInfo();
                    changeEmailButton.setSelected(false);
                } catch (Exception e) {
                    errorText.setText(e.getMessage());
                }

            }
        });


        cancelEmailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                emailTextField.setText(oldEmail);
                changeEmailButton.setSelected(false);
            }
        });

        showUserInfo();
    }

    private String getEmailInput() throws Exception {
        String email = emailTextField.getText();

        if (email.equals("")) {
            throw new Exception("Empty email, please re-enter");
        }

        if (!SharedPreference.validateEmail(email)) {
            throw new Exception("Invalid email, please re-enter");
        }
        return email;
    }

    private void showUserInfo() {
        UserDTO dto = UserManager.getManager().getLoggedInUserInfo();
        usernameText.setText(dto.getUsername());
        emailTextField.setText(dto.getEmail());
    }

}
