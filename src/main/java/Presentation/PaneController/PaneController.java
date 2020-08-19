package Presentation.PaneController;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class PaneController implements Initializable {
    public Pane parent;
    public FXMLLoader loader;

    public PaneController(Pane parent, String fxmlFile) {
        this.parent = parent;
        loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(this);
    }

    public void load() {
        try {
            Pane pane = loader.load();
            parent.getChildren().clear();
            parent.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
