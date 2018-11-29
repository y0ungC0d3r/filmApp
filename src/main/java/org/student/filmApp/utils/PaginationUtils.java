package org.student.filmApp.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.student.filmApp.Consts.DEFAULT_NUMBER_OF_PAGES;

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

    public static void main(String ...args) {
        System.out.println(getPaginationRange(5, 7));
    }
}
