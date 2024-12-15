package constant;

import java.time.format.DateTimeFormatter;

public final class CommonConstants {
    public static final String LOCAL_DATE_TIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";
}
