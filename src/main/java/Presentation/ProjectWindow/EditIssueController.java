package Presentation.ProjectWindow;

import Business.SharedPreference;
import DTO.IssueDTO;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.*;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditIssueController implements Initializable {
    Stage stage;
    ProjectMainCotroller mediator;
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
    JFXComboBox<SharedPreference.IssueStatus> statusComboBox;
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

    public EditIssueController(IssueDTO dto, ProjectMainCotroller mediator) {
        this.mediator = mediator;
        labels = FXCollections.observableArrayList(mediator.manager.getProjectLabels());
        labels.add("");
        members = FXCollections.observableArrayList(mediator.manager.getMemberNameList());
        this.dto = dto;
    }

    public void load() {
        try {
            FXMLLoader loader = loader = new FXMLLoader(getClass()
                    .getResource("/ProjectWindow/issue-input.fxml"));
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
                            setText("");
                        }
                    }

                };
                return listCell;
            }
        });

        statusComboBox.setItems(FXCollections.observableArrayList(SharedPreference.IssueStatus.values()));
        statusComboBox.setCellFactory(new Callback<ListView<SharedPreference.IssueStatus>,
                ListCell<SharedPreference.IssueStatus>>() {
            @Override
            public ListCell<SharedPreference.IssueStatus> call(ListView<SharedPreference.IssueStatus> statusListView) {
                final ListCell<SharedPreference.IssueStatus> listCell = new ListCell<SharedPreference.IssueStatus>() {
                    @Override
                    protected void updateItem(SharedPreference.IssueStatus t, boolean bln) {
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
                    mediator.manager.updateIssue(dto);
                    stage.close();
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
                stage.close();
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

        showIssueInfo();
    }

    private void getIssueInput() throws Exception{
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

    private void showIssueInfo() {
        titleTextField.setText(dto.getTitle());
        descriptionTextArea.setText(dto.getDescription());
        statusComboBox.getSelectionModel().select(dto.getStatus());

        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals(dto.getLabel())) {
                labelComboBox.getSelectionModel().select(labels.get(i));
            }
        }

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).equals(dto.getAssignee())) {
                assigneeComboBox.getSelectionModel().select(members.get(i));
            }
        }

        setDueDate();

        noteTextArea.setText(dto.getNote());
    }

    private void setDueDate() {
        try {
            LocalDateTime date = SharedPreference.getDateFormat().parse(dto.getDueDate()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            datePicker.setValue(date.toLocalDate());
            timePicker.set24HourView(true);
            timePicker.setValue(date.toLocalTime());
        } catch (ParseException e) {
            //do nothing
        }
    }
}
