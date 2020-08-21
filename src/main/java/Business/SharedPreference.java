package Business;

import java.text.SimpleDateFormat;

public final class SharedPreference {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    public enum IssueStatus {
        OPEN("OPEN",0),
        WORKING("WORKING", 1),
        RESOLVED("RESOLVED",2);

        public final int value;
        public final String label;

        private IssueStatus(String label, int value) {
            this.label = label;
            this.value = value;
        }
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}
