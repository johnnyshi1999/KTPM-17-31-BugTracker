package Presentation.HomeWindow.Panes;

import Business.UserManager;
import DTO.MemberDTO;
import DTO.ProjectDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.HomeWindow.MainController;
import Presentation.CreateProjectController;
import Presentation.ProjectWindow.ProjectMainCotroller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController extends PaneController<MainController> {
    ObservableList<ProjectDTO> projects;
    ObservableList<ProjectDTO> searchResult;
    @FXML
    TableView<ProjectDTO> projectTableView;
    @FXML
    TableColumn<ProjectDTO, String> nameColumn;
    @FXML
    TableColumn<ProjectDTO, Integer> issueColumn;
    @FXML
    TableColumn<ProjectDTO, Integer> memberColumn;
    @FXML
    TableColumn<ProjectDTO, Integer> creatorColumn;
    @FXML
    TableColumn<ProjectDTO, String> dateCreatedColumn;
    @FXML
    Button newProjectButton;
    @FXML
    Button deleteButton;
    @FXML
    TextField keywordTextField;


    public HomeController(MainController controller)
    {
        super(controller.getParentPane(), "/MainWindow/home.fxml");
        mediator = controller;
        projects = FXCollections.observableArrayList(UserManager.getManager().getUserProjects());
        searchResult = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, String>("title"));
        nameColumn.setStyle( "-fx-alignment: CENTER-LEFT;");

        issueColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, Integer>("openIssues"));

        memberColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, Integer>("members"));

        creatorColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, Integer>("creator"));

        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<ProjectDTO, String>("dateCreated"));

        ListChangeListener<ProjectDTO> listener = new ListChangeListener<ProjectDTO>() {
            @Override
            public void onChanged(Change<? extends ProjectDTO> change) {
                while (change.next()) {
                    projectTableView.refresh();
                }
            }
        };

        projects.addListener(listener);
        searchResult.addListener(listener);

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

        projectTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProjectDTO>() {
            @Override
            public void changed(ObservableValue<? extends ProjectDTO> observableValue, ProjectDTO dto, ProjectDTO t1) {
                if (t1 == null) {
                    deleteButton.setDisable(true);
                }
                else {
                    deleteButton.setDisable(false);
                }
            }
        });
        deleteButton.setDisable(false);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProjectDTO selected = projectTableView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Delete Project Confirmation");
                alert.setContentText("Are you sure to delete:\n" + selected.getTitle());
                DialogPane pane = alert.getDialogPane();
                pane.getStylesheets().add(
                        getClass().getResource("/css/dialog.css").toExternalForm());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    keywordTextField.clear();
                    UserManager.getManager().deleteProject(selected);
                    projects.remove(selected);
                }


            }
        });

        newProjectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreateProjectController controller = new CreateProjectController();
                controller.load();
                if (controller.getDTO() != null) {
                    projects.add(controller.getDTO());
                }
            }
        });

        keywordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.equals("")) {
                    searchResult.clear();
                    searchResult.addAll(searchProject(t1));
                    projectTableView.setItems(searchResult);
                }
                else {
                    projectTableView.setItems(projects);
                }
            }
        });

    }

    private List<ProjectDTO> searchProject(String keyword) {
        List<ProjectDTO> result = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getTitle().contains(keyword)) {
                result.add(projects.get(i));
                continue;
            }

//            if (projects.get(i).getDescription().contains(keyword)) {
//                result.add(projects.get(i));
//                continue;
//            }

            if (projects.get(i).getCreator().contains(keyword)) {
                result.add(projects.get(i));
                continue;
            }
        }
        return result;
    }

    @Override
    public void checkAccess() {
    }
}
