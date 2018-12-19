package org.student.filmApp.utils;

import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.student.filmApp.Consts.DEFAULT_NUMBER_OF_PAGES;
import static org.student.filmApp.Consts.DEFAULT_PAGE_NUMBER;
import static org.student.filmApp.Consts.DEFAULT_PAGE_SIZE;
import static org.student.filmApp.utils.CollectionUtils.emptyIfNull;

public class PaginationUtils {
    public static List<Integer> getPaginationRange(int currPageNumber, int numberOfPages) {
        int maxOffset = DEFAULT_NUMBER_OF_PAGES / 2;

        int startPageNumber = currPageNumber - maxOffset;
        if(startPageNumber < 1) {
            startPageNumber = 1;
        }

        int lastPageNumber = currPageNumber + maxOffset;
        if(lastPageNumber > numberOfPages) {
            lastPageNumber = numberOfPages;
        }

        return IntStream
                .rangeClosed(startPageNumber, lastPageNumber)
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

    public static int calculateCurrPageNumber(List<String> currPageNumberList, int lastPageNumber) {

        try {
            int currPageNumber = emptyIfNull(currPageNumberList)
                    .stream()
                    .findFirst()
                    .map(Integer::parseInt)
                    .orElseThrow(NullPointerException::new);

            return currPageNumber > lastPageNumber ? lastPageNumber : currPageNumber;
        } catch (NumberFormatException | NullPointerException e) {
            return DEFAULT_PAGE_NUMBER;
        }
    }

    public static void main(String ...args) {
        System.out.println(getPaginationRange(2, 10));
        System.out.println(calculateNumberOfPages(13));
        System.out.println(emptyIfNull(null)
                .stream()
                .findFirst()
                .orElse(null));
    }
}
