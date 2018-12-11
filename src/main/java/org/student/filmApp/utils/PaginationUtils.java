package org.student.filmApp.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.student.filmApp.Consts.DEFAULT_NUMBER_OF_PAGES;
import static org.student.filmApp.Consts.DEFAULT_PAGE_SIZE;

public class PaginationUtils {
    public static Map<Integer, Boolean> getPaginationRange(int currPageNumber, int numberOfPages) {
        int maxOffset = DEFAULT_NUMBER_OF_PAGES / 2;

        int startPageNumber = currPageNumber - maxOffset;
        if(startPageNumber < 1) {
            startPageNumber = 1;
        }

        int lastPageNumber = currPageNumber + maxOffset;
        if(lastPageNumber > numberOfPages) {
            lastPageNumber = numberOfPages;
        }

        LinkedHashMap<Integer, Boolean> markedNumberOfPages = new LinkedHashMap<>();
        for (int i = startPageNumber; i <= lastPageNumber; i++) {
            markedNumberOfPages.put(i, i == currPageNumber);
        }

        return markedNumberOfPages;
    }

    public static int calculateNumberOfPages(long numberOfRows) {
        if(numberOfRows < 1) {
            return 1;
        }
        if(numberOfRows % DEFAULT_PAGE_SIZE == 0) {
            return (int) (numberOfRows / DEFAULT_PAGE_SIZE);
        }
        return (int) ((numberOfRows / DEFAULT_PAGE_SIZE) + 1);
    }

    public static void main(String ...args) {
        System.out.println(getPaginationRange(1, 1));
        System.out.println(calculateNumberOfPages(13));
    }
}
