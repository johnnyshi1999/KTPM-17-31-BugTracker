package Presentation;

import Business.UserManager;
import DTO.UserDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Stage stage;
    UserDTO dto = null;

    @FXML
    JFXTextField usernameTextField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    Text errorText;
    @FXML
    JFXButton loginButton;
    @FXML
    JFXButton registerButton;

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass().getResource("/login.fxml"));
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
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameTextField.getText();
                String password = passwordField.getText();

                if (username.equals("")) {
                    errorText.setText("Empty username, please enter username");
                }

                boolean success = UserManager.getManager().setLoggedInUser(username, password);
                if (success) {
                    dto = UserManager.getManager().getLoggedInUserInfo();
                    stage.close();
                }
                else {
                    errorText.setText("Incorrect username or password");
                }
            }
        });

        LoginController controller = this;
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegisterController c = new RegisterController();
                c.load();

                if (c.dto != null) {
                    dto = c.dto;
                    stage.close();
                }
            }
        });
    }
}
