package Presentation.ProjectWindow.Pane;

import Business.ProjectManager;
import Business.UserManager;
import DTO.IssueDTO;
import DTO.MemberDTO;
import DTO.ProjectDTO;
import Entities.Issue;
import Presentation.CustomControllers.PaneController;
import Presentation.ProjectWindow.EditIssueController;
import Presentation.ProjectWindow.IssueInfoController;
import Presentation.ProjectWindow.ProjectInfoController;
import Presentation.ProjectWindow.ProjectMainCotroller;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectHomeController extends PaneController<ProjectMainCotroller> {
    ProjectManager manager;
    ObservableList<IssueDTO> issues;
    ObservableList<IssueDTO> searchResult;
    @FXML
    TableView<IssueDTO> issueTableView;
    @FXML
    TableColumn<IssueDTO, String> titleColumn;
    @FXML
    TableColumn<IssueDTO, String> labelColumn;
    @FXML
    TableColumn<IssueDTO, String> statusColumn;
    @FXML
    TableColumn<IssueDTO, String> assigneeColumn;
    @FXML
    TableColumn<IssueDTO, String> dueDateColumn;
    @FXML
    JFXCheckBox titleCheckBox;
    @FXML
    JFXCheckBox descriptionCheckBox;
    @FXML
    JFXCheckBox usernameCheckBox;
    @FXML
    Button deleteButton;
    @FXML
    TextField keywordTextField;

    EventHandler<ActionEvent> deleteIssueHandler;


    public ProjectHomeController(ProjectMainCotroller mediator) {
        super(mediator.getParentPane(), "/ProjectWindow/home.fxml");
        this.mediator = mediator;
        manager = mediator.manager;
        issues = FXCollections.observableArrayList(manager.getProjectIssues());
        searchResult = FXCollections.observableArrayList();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("title"));
        labelColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("label"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("status"));
        assigneeColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("assignee"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("dueDate"));

        issueTableView.setItems(issues);

        ListChangeListener<IssueDTO> listener = new ListChangeListener<IssueDTO>() {
            @Override
            public void onChanged(Change<? extends IssueDTO> change) {
                while (change.next()) {
                    issueTableView.refresh();
                }
            }
        };

        issues.addListener(listener);
        searchResult.addListener(listener);

        issueTableView.setRowFactory( tv -> {
            TableRow<IssueDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IssueDTO rowData = row.getItem();
                    IssueInfoController controller = new IssueInfoController(rowData);
                    controller.load();
                    if (controller.requestEdit == true) {
                        EditIssueController editIssueController = new EditIssueController(rowData, mediator);
                        editIssueController.load();
                        issueTableView.refresh();
                    }
                }
            });
            return row ;
        });

        deleteIssueHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IssueDTO issueDTO = issueTableView.getSelectionModel().getSelectedItem();
                if (issueDTO != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Delete Issue Confirmation");
                    alert.setContentText("Are you sure to delete this issue:\n" + issueDTO.getTitle());
                    DialogPane pane = alert.getDialogPane();
                    pane.getStylesheets().add(
                            getClass().getResource("/css/dialog.css").toExternalForm());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        keywordTextField.clear();
                        manager.deleteIssue(issueDTO);
                        issues.remove(issueDTO);
                    }


                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete issue");
                    alert.setHeaderText(null);
                    alert.setGraphic(null);
                    alert.setContentText("Please choose an issue");
                    DialogPane pane = alert.getDialogPane();
                    pane.getStylesheets().add(
                            getClass().getResource("/css/dialog.css").toExternalForm());
                    alert.showAndWait();
                }
            }
        };

        deleteButton.setOnAction(deleteIssueHandler);

        keywordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.equals("")) {
                    searchResult.clear();
                    searchResult.addAll(searchIssue(t1));
                    issueTableView.setItems(searchResult);
                }
                else {
                    issueTableView.setItems(issues);
                }
            }
        });


    }

    public ObservableList<IssueDTO> getIssues() {
        return issues;
    }

    @Override
    public void checkAccess() {
        if (mediator.manager.getAssignRight()) {
            deleteButton.setOnAction(deleteIssueHandler);
        }
        else {
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Access Denied");
//                    alert.setContentText("You don't have Team Managing right\n" + "Contact your team manager to know more about this");
                    DialogPane pane = alert.getDialogPane();
                    pane.setContent(new Label("You don't have Task Managing right\n" + "Contact your team manager to know more about this\n"));
                    pane.getStylesheets().add(
                            getClass().getResource("/css/dialog.css").toExternalForm());
                    alert.showAndWait();
                }
            });
        }
    }

    private List<IssueDTO> searchIssue(String keyword) {
        List<IssueDTO> result = new ArrayList<>();

        for (int i = 0; i < issues.size(); i++) {
            if (titleCheckBox.isSelected()) {
                if (issues.get(i).getTitle().contains(keyword)) {
                    result.add(issues.get(i));
                    break;
                }
            }

            if (descriptionCheckBox.isSelected()) {
                if (issues.get(i).getDescription().contains(keyword)) {
                    result.add(issues.get(i));
                    break;
                }
            }

            if (usernameCheckBox.isSelected()) {
                if (issues.get(i).getAssignee().contains(keyword)) {
                    result.add(issues.get(i));
                    break;
                }
            }
        }
        return result;
    }

}
