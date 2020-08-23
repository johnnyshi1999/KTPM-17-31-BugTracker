package Presentation;

import Business.SharedPreference;
import Business.UserManager;
import DTO.UserDTO;
import Entities.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    Stage stage;
    UserDTO dto = null;

    @FXML
    JFXTextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    JFXTextField emailTextField;
    @FXML
    JFXButton okButton;
    @FXML
    JFXButton backButton;
    @FXML
    Text errorText;

    public void load() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            stage = new Stage();
            stage.setTitle("Login to system");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dto = createUser();
                    stage.close();
                } catch (Exception e) {
                    errorText.setText(e.getMessage());
                    return;
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dto = null;
                stage.close();
            }
        });
    }

    private UserDTO createUser() throws Exception{
        UserDTO userDTO = new UserDTO();

        String username = usernameTextField.getText();

        if (username.contains(" ")) {
            throw new Exception("Username can't have space between");
        }

        String password = passwordField.getText();
        if (password.equals("")) {
            throw new Exception("Empty password, please re-enter");
        }

        if (!password.equals(confirmPasswordField.getText())) {
            throw new Exception("Password confirmation is not match, please re-enter");
        }

        String email = emailTextField.getText();
        if (email.equals("")) {
            throw new Exception("Empty email, please re-enter");
        }

        if (!SharedPreference.validateEmail(email)) {
            throw new Exception("Invalid email, please re-enter");
        }

        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setEmail(email);
        try {
            UserManager.getManager().registerUser(userDTO);
        } catch (Exception e) {
            throw e;
        }
        return userDTO;
    }
}
