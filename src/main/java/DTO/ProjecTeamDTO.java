package DTO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ProjecTeamDTO {
    private String userId;
    private BooleanProperty assignRight = new SimpleBooleanProperty();
    private BooleanProperty inviteRight = new SimpleBooleanProperty();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
