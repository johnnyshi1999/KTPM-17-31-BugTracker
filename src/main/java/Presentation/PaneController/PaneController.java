package Presentation.PaneController;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PaneController implements Initializable {
    public Pane parent;
    public FXMLLoader loader;

    public PaneController(Pane parent, String fxmlFile) {
        this.parent = parent;
        loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(this);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
