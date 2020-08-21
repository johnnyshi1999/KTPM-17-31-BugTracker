package Presentation.ProjectWindow.Pane;

import Business.ProjectManager;
import Business.SharedPreference;
import Business.SharedPreference.IssueStatus;
import DTO.IssueDTO;
import Presentation.PaneController.PaneController;
import Presentation.ProjectWindow.ProjectMainCotroller;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AddIssueController extends PaneController {
    ProjectMainCotroller mediator;
    IssueDTO dto = null;
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
    Text errorText;
    @FXML
    ScrollPane scrollPane;
    public AddIssueController(Pane parent, ProjectMainCotroller mediator) {
        super(parent, "/ProjectWindow/issue-input.fxml");
        this.mediator = mediator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assignPane.disableProperty().bind(assignToggleButton.selectedProperty().not());
        assignToggleButton.setDisable(mediator.manager.getAssignRight() == false);

        statusComboBox.setItems(FXCollections.observableArrayList(IssueStatus.values()));
        statusComboBox.setCellFactory(new Callback<ListView<IssueStatus>,
                ListCell<IssueStatus>>() {
            @Override
            public ListCell<IssueStatus> call(ListView<IssueStatus> locationListView) {
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
            dto.setAssignee(assigneeComboBox.getSelectionModel().getSelectedItem());
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
