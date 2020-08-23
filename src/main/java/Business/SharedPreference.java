package Business;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";// cấu trúc 1 email thông thường


    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
