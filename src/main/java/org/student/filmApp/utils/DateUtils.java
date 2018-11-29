package org.student.filmApp.utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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

    public static LocalDate getFirstDayOfYear(int year) {
        return LocalDate.of(year, Month.JANUARY, 1);
    }

    public static LocalDate getLastDayOfYear(int year) {
        return LocalDate.of(year, Month.DECEMBER, 31);
    }
}
