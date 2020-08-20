package Business;

import Business.Notifier;

import java.util.ArrayList;
import java.util.List;

public class INotifyChange {
    private List<Notifier> notifiers = new ArrayList<>();

    public List<Notifier> getNotifiers() {
        return notifiers;
    }

    public void fireNotifiers() {
        for (int i = 0; i < notifiers.size(); i++) {
            notifiers.get(i).notifyChange(this);
        }
    }

}
