package org.student.filmApp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.student.filmApp.entity.Actor;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.Person;
import org.student.filmApp.repository.ActorRepository;
import org.student.filmApp.repository.ActorRepository2;
import org.student.filmApp.repository.CountryRepository;
import org.student.filmApp.repository.FilmRepository;
import org.student.filmApp.repository.PersonRepository;
import org.student.filmApp.utils.DateUtils;

//@Controller
public class Test {
	public static void main(String ...args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		Class du = Class.forName("org.student.filmApp.utils.DateUtils");
		DateUtils dut = (DateUtils) du.newInstance();
		for(int i = 0; i < 100; i++) {
			System.out.println("insert into film(id, polish_release_date) values(" + (i + 2) + ", TO_DATE('" + (1990 + ThreadLocalRandom.current().nextInt(29)) + "', 'yyyy'));");
		}

		List<String> strings = Arrays.asList("55", "6", "3");

		strings.sort((s1, s2) -> {
			if(s1.length() == s2.length()) {
				return s1.compareTo(s2);
			}
			return s1.length() - s2.length();
		});
		System.out.println(strings);

		convertToDatabaseColumn(LocalDate.of(2013, Month.JANUARY, 1));
	}

	private static List<AbstractMap.SimpleEntry<Integer, Integer>> mergeIntervals(List<Integer> years) {

		final int START_INDEX = 0;
		final int END_INDEX = years.size() - 1;

		years.sort(Integer::compareTo);

		List<AbstractMap.SimpleEntry<Integer, Integer>> intervals = new ArrayList<>();

		int originFrom = years.get(START_INDEX);
		for (int i = START_INDEX; i < years.size() - 1; i++) {
			int from = years.get(i);
			int to = years.get(i+1);
			if(from != to - 1 || i + 1 == years.size() - 1) {
				intervals.add(getPair(originFrom, from));
				originFrom = years.get(i+1);
				System.out.println(originFrom);
			}
		}


		return intervals;
	}

	private static AbstractMap.SimpleEntry<Integer, Integer> getPair(Integer from, Integer to) {
		return new AbstractMap.SimpleEntry<>(from, to);
	}

	public static java.sql.Date convertToDatabaseColumn(LocalDate localDate) {
		return Date.valueOf(localDate);
	}
}
