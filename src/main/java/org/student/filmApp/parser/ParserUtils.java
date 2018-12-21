package org.student.filmApp.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ParserUtils {
    public static Optional<String> convertToRequiredDateFormat(String date) throws ParseException {
        String[] dateParts = date.split(" ");
        String dayOfMonth = dateParts[0].length() == 1 ? "0" + dateParts[0] : dateParts[0];
        String month = convertMonth(dateParts[1]);
        String year = dateParts[2];
        return Optional.of(dayOfMonth + month + year);
    }

    public static String convertMonth(String month) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = dateFormat.parse(month);
        dateFormat.applyPattern("MM");
        return dateFormat.format(date);
    }
}
