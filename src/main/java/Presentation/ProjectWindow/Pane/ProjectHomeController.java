package Presentation.ProjectWindow.Pane;

import Business.ProjectManager;
import DTO.IssueDTO;
import DTO.ProjectDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.ProjectWindow.EditIssueController;
import Presentation.ProjectWindow.IssueInfoController;
import Presentation.ProjectWindow.ProjectMainCotroller;
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
import java.util.ResourceBundle;

public class ProjectHomeController extends PaneController<ProjectMainCotroller> {
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
    Button deleteButton;

    ProjectManager manager;
    ObservableList<IssueDTO> issues;

    public ProjectHomeController(ProjectMainCotroller mediator) {
        super(mediator.getParentPane(), "/ProjectWindow/home.fxml");
        this.mediator = mediator;
        manager = mediator.manager;
        issues = FXCollections.observableArrayList(manager.getProjectIssues());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("title"));
        labelColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("label"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("status"));
        assigneeColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("assignee"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<IssueDTO, String>("dueDate"));

        issueTableView.setItems(issues);

        issues.addListener(new ListChangeListener<IssueDTO>() {
            @Override
            public void onChanged(Change<? extends IssueDTO> change) {
                while (change.next()) {
                    issueTableView.refresh();
                }
            }
        });

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

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IssueDTO issueDTO = issueTableView.getSelectionModel().getSelectedItem();
                if (issueDTO != null) {
                    manager.deleteIssue(issueDTO);
                    issues.remove(issueDTO);
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
        });

    }

    public ObservableList<IssueDTO> getIssues() {
        return issues;
    }

    @Override
    public void checkAccess() {

    }
}
