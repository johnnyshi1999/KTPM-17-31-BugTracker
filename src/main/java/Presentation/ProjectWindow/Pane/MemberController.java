package Presentation.ProjectWindow.Pane;

import DTO.MemberDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.ProjectWindow.InviteMemberController;
import Presentation.ProjectWindow.ProjectMainCotroller;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberController extends PaneController {
    ProjectMainCotroller mediator;
    ObservableList<MemberDTO> members;
    ObservableList<MemberDTO> searchResult;

    @FXML
    TextField searchTextField;
    @FXML
    TableView<MemberDTO> memberTableView;
    @FXML
    TableColumn<MemberDTO, String> usernameColumn;
    @FXML
    TableColumn<MemberDTO, String> assignColumn;
    @FXML
    TableColumn<MemberDTO, String> inviteColumn;

    @FXML
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;

    @FXML
    TextField usernameText;
    @FXML
    TextField emailText;
    @FXML
    TextField dateJoinedText;
    @FXML
    JFXCheckBox assignCheckBox;
    @FXML
    JFXCheckBox inviteCheckBox;

    public MemberController(ProjectMainCotroller mainCotroller) {
        super(mainCotroller.getParentPane(), "/ProjectWindow/member.fxml");
        mediator = mainCotroller;
        members = FXCollections.observableArrayList(mediator.manager.getMemberList());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("username"));

        assignColumn.setCellValueFactory(cellData -> {
            boolean right = cellData.getValue().isAssignRight();
            String string;
            if (right == true) {
                string = "Yes";
            } else {
                string = "No";
            }

            return new ReadOnlyStringWrapper(string);
        });

        inviteColumn.setCellValueFactory(cellData -> {
            boolean right = cellData.getValue().isInviteRight();
            String string;
            if (right == true) {
                string = "Yes";
            } else {
                string = "No";
            }

            return new ReadOnlyStringWrapper(string);
        });

        memberTableView.setItems(members);
        members.addListener(new ListChangeListener<MemberDTO>() {
            @Override
            public void onChanged(Change<? extends MemberDTO> change) {
                while (change.next()) {
                    memberTableView.refresh();
                }
            }
        });

        memberTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberDTO>() {
            @Override
            public void changed(ObservableValue<? extends MemberDTO> observableValue, MemberDTO dto, MemberDTO t1) {
                showMemberInfo(t1);
                if (t1 != null) {
                    editButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
                else {
                    editButton.setDisable(true);
                    deleteButton.setDisable(true);
                }
            }
        });
        memberTableView.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InviteMemberController dialog = new InviteMemberController(mediator.manager);
                dialog.load();
                if (dialog.dto != null) {
                    clearSearch();
                    members.add(dialog.dto);
                    memberTableView.getSelectionModel().select(dialog.dto);
                }
            }
        });

    }

    private void clearSearch() {
        searchTextField.clear();
        memberTableView.setItems(members);
    }

    private void showMemberInfo(MemberDTO dto) {

    }
}
