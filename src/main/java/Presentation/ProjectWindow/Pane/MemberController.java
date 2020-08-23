package Presentation.ProjectWindow.Pane;

import DTO.MemberDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.ProjectWindow.EditMemberController;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberController extends PaneController<ProjectMainCotroller> {
    ObservableList<MemberDTO> members;
    ObservableList<MemberDTO> searchResult;

    EventHandler<ActionEvent> addMemberHandler;
    EventHandler<ActionEvent> editMemberHandler;
    EventHandler<ActionEvent> deleteMemberHandler;

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
        searchResult = FXCollections.observableArrayList();
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

        ListChangeListener<MemberDTO> listener = new ListChangeListener<MemberDTO>() {
            @Override
            public void onChanged(Change<? extends MemberDTO> change) {
                while (change.next()) {
                    memberTableView.refresh();
                }
            }
        };
        memberTableView.setItems(members);
        members.addListener(listener);
        searchResult.addListener(listener);

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

        addMemberHandler = new EventHandler<ActionEvent>() {
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
        };
        addButton.setOnAction(addMemberHandler);

        editMemberHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MemberDTO selected = memberTableView.getSelectionModel().getSelectedItem();
                EditMemberController dialog = new EditMemberController(mediator.manager, selected);
                dialog.load();
                memberTableView.refresh();
                memberTableView.getSelectionModel().select(dialog.dto);
            }
        };
        editButton.setOnAction(editMemberHandler);

        deleteMemberHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MemberDTO selected = memberTableView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Remove Member Confirmation");
                alert.setContentText("Are you sure?");
                DialogPane pane = alert.getDialogPane();
                pane.getStylesheets().add(
                        getClass().getResource("/css/dialog.css").toExternalForm());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    clearSearch();
                    mediator.manager.removeMember(selected);
                    members.remove(selected);
                }
            }
        };
        deleteButton.setOnAction(deleteMemberHandler);

        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.equals("")) {
                    searchResult.clear();
                    searchResult.addAll(searchMember(t1));
                    memberTableView.setItems(searchResult);
                }
                else {
                    memberTableView.setItems(members);
                }
            }
        });
    }

    private void clearSearch() {
        searchTextField.clear();
//        memberTableView.setItems(members);
    }

    private void showMemberInfo(MemberDTO dto) {

    }

    private List<MemberDTO> searchMember(String username) {
        List<MemberDTO> result = new ArrayList<>();

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getUsername().contains(username)) {
                result.add(members.get(i));
            }
        }
        return result;
    }

    @Override
    public void checkAccess() {
        if (mediator.manager.getInviteRight()) {
            addButton.setOnAction(addMemberHandler);
            editButton.setOnAction(editMemberHandler);
            deleteButton.setOnAction(deleteMemberHandler);
        }
        else {
            EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Access Denied");
//                    alert.setContentText("You don't have Team Managing right\n" + "Contact your team manager to know more about this");
                    DialogPane pane = alert.getDialogPane();
                    pane.setContent(new Label("You don't have Team Managing right\n" + "Contact your team manager to know more about this\n"));
                    pane.getStylesheets().add(
                            getClass().getResource("/css/dialog.css").toExternalForm());
                    alert.showAndWait();
                }
            };

            addButton.setOnAction(handler);
            editButton.setOnAction(handler);
            deleteButton.setOnAction(handler);
        }
    }
}
