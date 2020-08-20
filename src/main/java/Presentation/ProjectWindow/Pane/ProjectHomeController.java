package Presentation.ProjectWindow.Pane;

import Business.ProjectManager;
import DTO.IssueDTO;
import DTO.ProjectDTO;
import Presentation.PaneController.PaneController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectHomeController extends PaneController {
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

    ProjectManager manager;
    ObservableList<IssueDTO> issues;

    public ProjectHomeController(Pane parent, ProjectManager manager) {
        super(parent, "/ProjectWindow/home.fxml");
        this.manager = manager;
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

    }
}
