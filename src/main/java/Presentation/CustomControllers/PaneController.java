package Presentation.CustomControllers;

import Business.Notifer.INotifyChange;
import Business.Notifer.Notifier;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class PaneController<T> implements Initializable {
    public Pane parent;
    public Pane container = null;
    protected T mediator;
    public FXMLLoader loader;

    public PaneController(Pane parent, String fxmlFile) {
        this.parent = parent;
        loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(this);
        setUserAccessListener();
    }

    public void load() {
        if (container == null) {
            try {
                container = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        parent.getChildren().clear();
        parent.getChildren().add(container);
        checkAccess();
    }

    protected void setUserAccessListener() {
        Notifier notifier = new Notifier() {
            @Override
            public void notifyChange(INotifyChange t) {
                checkAccess();
            }
        };

//        UserManager.getManager().getNotifiers().add(notifier);
    }

    public abstract void checkAccess();
}
