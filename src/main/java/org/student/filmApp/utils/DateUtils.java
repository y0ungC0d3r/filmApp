package org.student.filmApp.utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtils {
    public static List<Integer> getYears() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return IntStream
                .rangeClosed(1900, currentYear)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }
}
