package Presentation.ProjectWindow.Pane;

import Presentation.PaneController.PaneController;
import Presentation.ProjectWindow.ProjectMainCotroller;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberController extends PaneController {
    ProjectMainCotroller mediator;

    public MemberController(ProjectMainCotroller mainCotroller) {
        super(mainCotroller.getParentPane(), "/ProjectWindow/member.fxml");
        mediator = mainCotroller;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
