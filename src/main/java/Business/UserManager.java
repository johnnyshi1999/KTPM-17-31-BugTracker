package Business;

import DAO.UserDAO;
import Entities.User;

public class UserManager {
    UserDAO userDAO = new UserDAO();
    private User loggedInUser = null;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String username, String password) {
        loggedInUser = userDAO.getLoggedInUser(username, password);
    }
}
