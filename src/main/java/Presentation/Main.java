package Presentation;

import DAO.UserDAO;
import Entities.Assignment;
import Entities.User;
import Presentation.HomeWindow.MainController;
import hibernate.HibernateUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow/main.fxml"));
        primaryStage.setTitle("BugTracker");
        MainController mainController = new MainController();
        loader.setController(mainController);

        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String sql = "Select e from " + Assignment.class.getName() + " e";
        Query<Assignment> query = session.createQuery(sql);
        List<Assignment> conferenceList = query.getResultList();
        session.getTransaction().commit();
        User user = new UserDAO().getLoggedInUser("user1", "user");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
