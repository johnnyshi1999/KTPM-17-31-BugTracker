package Presentation.ProjectWindow.Pane;

import Business.SharedPreference;
import Business.SharedPreference.IssueStatus;
import DTO.IssueDTO;
import Presentation.CustomControllers.PaneController;
import Presentation.ProjectWindow.ProjectMainCotroller;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddIssueController extends PaneController<ProjectMainCotroller> {
    IssueDTO dto = null;
    ObservableList<String> labels;
    ObservableList<String> members;
    @FXML
    TextField titleTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    JFXComboBox<String> labelComboBox;
    @FXML
    JFXComboBox<IssueStatus> statusComboBox;
    @FXML
    AnchorPane assignPane;
    @FXML
    JFXToggleButton assignToggleButton;
    @FXML
    ComboBox<String> assigneeComboBox;
    @FXML
    JFXDatePicker datePicker;
    @FXML
    JFXTimePicker timePicker;
    @FXML
    TextArea noteTextArea;

    @FXML
    JFXButton saveButton;
    @FXML
    JFXButton cancelButton;
    @FXML
    Button newLabelButton;
    @FXML
    Text errorText;
    @FXML
    ScrollPane scrollPane;
    public AddIssueController(ProjectMainCotroller mediator) {
        super(mediator.getParentPane(), "/ProjectWindow/issue-input.fxml");
        this.mediator = mediator;
        labels = FXCollections.observableArrayList(mediator.manager.getProjectLabels());
        labels.add(0,"");
        members = FXCollections.observableArrayList(mediator.manager.getMemberNameList());
    }

    @Override
    public void load() {
        super.load();
        assignToggleButton.setDisable(mediator.manager.getAssignRight() == false);
    }

    @Override
    public void checkAccess() {
        assignToggleButton.setDisable(mediator.manager.getAssignRight() == false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assignPane.disableProperty().bind(assignToggleButton.selectedProperty().not());
        assignToggleButton.setDisable(mediator.manager.getAssignRight() == false);

        labelComboBox.setItems(labels);
        labelComboBox.setCellFactory(new Callback<ListView<String>,
                ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> locationListView) {
                final ListCell<String> listCell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t);
                        }else{
                            setText("None");
                        }
                    }

                };
                return listCell;
            }
        });

        statusComboBox.setItems(FXCollections.observableArrayList(IssueStatus.values()));
        statusComboBox.setCellFactory(new Callback<ListView<IssueStatus>,
                ListCell<IssueStatus>>() {
            @Override
            public ListCell<IssueStatus> call(ListView<IssueStatus> statusListView) {
                final ListCell<IssueStatus> listCell = new ListCell<IssueStatus>() {
                    @Override
                    protected void updateItem(IssueStatus t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t.label);
                        }else{
                            setText(null);
                        }
                    }

                };
                return listCell;
            }
        });
        statusComboBox.getSelectionModel().select(0);
        statusComboBox.setDisable(true);

        assigneeComboBox.setItems(members);
        assigneeComboBox.setCellFactory(new Callback<ListView<String>,
                ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> locationListView) {
                final ListCell<String> listCell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t);
                        }else{
                            setText("None");
                        }
                    }

                };
                return listCell;
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    getIssueInput();
                    clearInput();
                    mediator.manager.createIssue(dto);
                    mediator.returnToHome();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorText.setText(e.getMessage());
                    scrollPane.setVvalue(0);
                }
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dto = null;
                mediator.returnToHome();
            }
        });

        newLabelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("New Label");
                dialog.setHeaderText("Enter new label name");
                dialog.setContentText("Name:");
                DialogPane pane = dialog.getDialogPane();
                pane.getStylesheets().add(
                        getClass().getResource("/css/dialog.css").toExternalForm());

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    String label = result.get();
                    labels.add(label);
                    mediator.manager.createLabel(label);
                }
            }
        });


    }

    private void getIssueInput() throws Exception{
        if (dto == null) {
            dto = new IssueDTO();
        }
        if (titleTextField.getText().equals("")) {
            throw new Exception("Empty title, please enter title");
        }
        dto.setTitle(titleTextField.getText());

        dto.setDescription(descriptionTextArea.getText());

        if (labelComboBox.getSelectionModel().getSelectedItem() != null) {
            dto.setLabel(labelComboBox.getSelectionModel().getSelectedItem());
        }

        dto.setStatus(statusComboBox.getSelectionModel().getSelectedItem());

        if (assignToggleButton.isSelected() == true) {
            String assignee = assigneeComboBox.getSelectionModel().getSelectedItem();
            if (assignee != null) {
                dto.setAssignee(assigneeComboBox.getSelectionModel().getSelectedItem());
            }
            else {
                throw new Exception("Assignment does not have Assignee");
            }
            dto.setNote(noteTextArea.getText());
            Date date = getDueDate();
            Date now = new Date();
            if (!date.after(now)) {
                throw new Exception("Invalid due date, chosen date must be after current timestamp");
            }
            dto.setDueDate(SharedPreference.getDateFormat().format(date));
        }
    }

    private Date getDueDate() {
        if (datePicker.getValue() == null || timePicker.getValue() == null) {
            return null;
        }
        LocalDate localDate = datePicker.getValue();
        LocalTime localTime = timePicker.getValue();
        Instant instant = localTime.atDate(localDate).
                atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    private void clearInput() {
        titleTextField.clear();
        descriptionTextArea.clear();
        labelComboBox.getSelectionModel().clearSelection();
        if (assignToggleButton.isSelected() == true) {
            assignToggleButton.setSelected(false);
            assigneeComboBox.getSelectionModel().clearSelection();
            noteTextArea.clear();
        }
        errorText.setText("");
    }

    public IssueDTO getDto() {
        return dto;
    }
}
