package Presentation.ProjectWindow;

import Business.ProjectManager;
import Business.TeamManager;
import Business.UserManager;
import DTO.MemberDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InviteMemberController implements Initializable {
    Stage stage;
    ProjectManager manager;
    public MemberDTO dto = null;

    @FXML
    TextField usernameTextField;
    @FXML
    JFXCheckBox assignCheckBox;
    @FXML
    JFXCheckBox inviteCheckBox;
    @FXML
    Text confirmText;

    @FXML
    JFXButton inviteButton;
    @FXML
    JFXButton cancelButton;

    public InviteMemberController(ProjectManager manager) {
        this.manager = manager;
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass()
                    .getResource("/popup/member-input.fxml"));
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
        inviteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    getInput();
                    manager.inviteUser(dto);
                    confirmText.setFill(Color.GREEN);
                    confirmText.setText("User invited successfully");
                    usernameTextField.setEditable(false);
                    assignCheckBox.setDisable(true);
                    inviteCheckBox.setDisable(true);
                    inviteButton.setDisable(true);
                } catch (Exception e) {
                    dto = null;
                    confirmText.setText(e.getMessage());
                    confirmText.setFill(Color.RED);
                }
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dto = null;
                stage.close();
            }
        });
    }

    private void getInput() throws Exception {
        dto = new MemberDTO();

        if (usernameTextField.getText().equals("")) {
            throw new Exception("Empty username field");
        }
        dto.setUsername(usernameTextField.getText());
        dto.setAssignRight(assignCheckBox.isSelected());
        dto.setInviteRight(inviteCheckBox.isSelected());

    }
}
