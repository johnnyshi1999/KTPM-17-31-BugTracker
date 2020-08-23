package Presentation.HomeWindow.Panes;

import Business.UserManager;
import DTO.ProjectDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.HomeWindow.MainController;
import Presentation.ProjectInfoController;
import Presentation.ProjectWindow.ProjectMainCotroller;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends PaneController<MainController> {
    @FXML
    TableView<ProjectDTO> projectTableView;
    @FXML
    TableColumn<ProjectDTO, String> nameColumn;
    @FXML
    TableColumn<ProjectDTO, Integer> issueColumn;
    @FXML
    TableColumn<ProjectDTO, Integer> memberColumn;
    @FXML
    TableColumn<ProjectDTO, String> dateCreatedColumn;
    @FXML
    Button newProjectButton;

    ObservableList<ProjectDTO> projects;

    public HomeController(Pane parent)
    {
        super(parent, "/MainWindow/home.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, String>("title"));
        nameColumn.setStyle( "-fx-alignment: CENTER-LEFT;");

        issueColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, Integer>("openIssues"));

        memberColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, Integer>("members"));

        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, String>("dateCreated"));

        projects = FXCollections.observableArrayList(UserManager.getManager().getUserProjects());
        projects.addListener(new ListChangeListener<ProjectDTO>() {
            @Override
            public void onChanged(Change<? extends ProjectDTO> change) {
                while (change.next()) {
                    projectTableView.refresh();
                }
            }
        });
        projectTableView.setItems(projects);

        projectTableView.setRowFactory( tv -> {
            TableRow<ProjectDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ProjectDTO rowData = row.getItem();
                    ProjectMainCotroller cotroller = new ProjectMainCotroller(rowData);
                    cotroller.load();

                }
            });
            return row ;
        });

        newProjectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProjectInfoController controller = new ProjectInfoController();
                controller.load();
                if (controller.getDTO() != null) {
                    projects.add(controller.getDTO());
                }
            }
        });



    }
    @Override
    public void checkAccess() {
//        if (UserManager.getManager().getLoggedInUser() == null) {
//            projects.clear();
//        }
//        else {
//            projects = FXCollections.observableArrayList(UserManager.getManager().getUserProjects());
//        }
    }
}
