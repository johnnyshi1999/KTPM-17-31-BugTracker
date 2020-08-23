package Presentation.HomeWindow;

import Business.UserManager;
import DTO.UserDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    Stage stage;

    String oldPassword;

    @FXML
    JFXPasswordField oldPasswordField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    JFXPasswordField confirmPasswordField;
    @FXML
    JFXButton saveButton;
    @FXML
    JFXButton cancelButton;
    @FXML
    Text errorText;

    public ChangePasswordController() {
        oldPassword = UserManager.getManager().getLoggedInUserInfo().getPassword();
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass()
                    .getResource("/popup/change-password.fxml"));
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
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String password = changePassword();
                    UserDTO dto = UserManager.getManager().getLoggedInUserInfo();
                    dto.setPassword(password);
                    UserManager.getManager().editUserInfo();
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

    public String changePassword() throws Exception {
        if (oldPasswordField.getText().equals(oldPassword) == false) {
            throw new Exception("Wrong old password, please re-enter");
        }

        String password = passwordField.getText();
        if (password.length() < 5) {
            throw new Exception("Password must be atleast 5 characters");
        }
        if (password.equals(confirmPasswordField.getText()) == false) {
            throw new Exception("Password confirmation is not match, please re-enter");
        }

        return password;
    }
}
