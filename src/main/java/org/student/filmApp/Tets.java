package org.student.filmApp;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

class A {//implements Comparable<A> {

    int x;
    int y;

    public A(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*@Override
    public int compareTo(A o) {
        return this.y - o.y;
    }*/
}

public class Tets {

    public static void main(String... args) {
        SortedSet<A> a = new TreeSet<>((x, y) -> x.x - y.x);
        for(int i = 0; i < 15; i++) {
            //System.out.println("a.add(new A(" + ThreadLocalRandom.current().nextInt(10) + ", " + ThreadLocalRandom.current().nextInt(10) + ");");
        }
        a.add(new A(4, 1));
        a.add(new A(6, 4));
        a.add(new A(0, 3));
        a.add(new A(1, 3));
        a.add(new A(0, 0));
        a.add(new A(0, 2));
        a.add(new A(7, 1));
        a.add(new A(5, 0));
        a.add(new A(0, 6));
        a.add(new A(2, 6));
        a.add(new A(7, 4));
        a.add(new A(7, 6));
        a.add(new A(8, 9));
        a.add(new A(7, 1));
        a.add(new A(0, 4));

        a.stream().map(x -> x.x).forEach(System.out::println);
    }
}
