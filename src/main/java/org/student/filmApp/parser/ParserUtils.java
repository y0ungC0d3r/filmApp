package org.student.filmApp.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

public class ParserUtils {
    public static final Function<String, String> DB_DATE_MAPPER = d -> "TO_DATE('" + d + "', 'DDMMYYYY')";
    public static final Function<String, String> DB_HEIGHT_MAPPER = h -> h.replace(" cm", "");
    public static final Function<String, String> DB_STRING_MAPPER = s -> "'" + s.replace("'", "''") + "'";

    public static Optional<String> convertToRequiredDateFormat(String date) throws ParseException {
        if (date == null) {
            return Optional.empty();
        }
        String[] dateParts = date.split(" ");
        String dayOfMonth = dateParts[0].length() == 1 ? "0" + dateParts[0] : dateParts[0];
        String month = convertMonth(dateParts[1]);
        String year = dateParts[2];
        return Optional.of(dayOfMonth + month + year);
    }

    private static String convertMonth(String month) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = dateFormat.parse(month);
        dateFormat.applyPattern("MM");
        return dateFormat.format(date);
    }
}
