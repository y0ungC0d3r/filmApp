package org.student.filmApp.utils;

import org.student.filmApp.domain.Identifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class CollectionUtils {
    public static <T extends Identifiable, C extends Collection<T>> Map<T, Boolean> createMarkedIdentifiableElementsMap(
            C allElements, Collection<String> selectedElements) {
        return allElements
                .stream()
                .collect(toMap(Function.identity(),
                        i -> selectedElements.contains(String.valueOf(i.getId()))));
    }

    public static <T extends Collection<Integer>> Map<String, Boolean> createMarkedIntegerElementsMap(
            T intCollection, Collection<String> selectedElements) {
        return intCollection
                .stream()
                .map(String::valueOf)
                .collect(toMap(Function.identity(), selectedElements::contains));
    }

    public static <T> Collection<T> emptyIfNull(Collection<T> c) {
        return c == null ? Collections.EMPTY_LIST :  c;
    }

    public static boolean isNullOrEmpty(Collection<?> c) {
            return (c == null || c.isEmpty());
    }
}
