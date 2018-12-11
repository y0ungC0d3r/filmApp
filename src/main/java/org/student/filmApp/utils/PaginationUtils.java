package org.student.filmApp.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.student.filmApp.Consts.DEFAULT_NUMBER_OF_PAGES;
import static org.student.filmApp.Consts.DEFAULT_PAGE_SIZE;

public class PaginationUtils {
    public static List<Integer> getPaginationRange(int currPageNumber, int numberOfPages) {
        int maxOffset = DEFAULT_NUMBER_OF_PAGES / 2;

        int startPageNumber = currPageNumber - maxOffset;
        if(startPageNumber < 1) {
            startPageNumber = 1;
        }

        int endPageNumber = currPageNumber + maxOffset;
        if(endPageNumber > numberOfPages) {
            endPageNumber = numberOfPages;
        }

        return IntStream
                .rangeClosed(startPageNumber, endPageNumber)
                .boxed()
                .collect(Collectors.toList());
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
        System.out.println(getPaginationRange(5, 7));
        System.out.println(calculateNumberOfPages(13));
    }
}
