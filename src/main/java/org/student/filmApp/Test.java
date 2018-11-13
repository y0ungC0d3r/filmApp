package org.student.filmApp;

import java.util.List;
import java.util.Objects;

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

@Controller
public class Test {
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	ActorRepository actorRepository;
	
	@Autowired
	FilmRepository filmRepository;

	@Transactional
	@RequestMapping(name = "/test")
	public String test() {

        Film film = filmRepository.findById(1L).orElse(null);
        film.getFilmActors().forEach(x -> System.out.println(x.getCharacter()));


		/*System.out.println(countryRepository.findAll().get(0).getCountryName());
		Person person = personRepository.findAll().get(0);
		System.out.println(person.getFullName());
		//System.out.println(personRepository.findAll().get(0).getActors());//.get(0).getCharacter());
		System.out.println("here");
		//System.out.println(p.);
		//personRepository.
		System.out.println(person);
		List<Film> films = filmRepository.findFilmByFilmDirectors(person);
		System.out.println(films.get(0).getOriginalTitle());
		System.out.println(personRepository.findById(Long.valueOf(1)));
		
		
		Person person2 = personRepository.findPersonAndDirectorFilms().get(0);
		//System.out.println(person2.getDirectorFilms().get(0).getOriginalTitle());
		
		person2 = personRepository.findPersonAndDirectorFilmsByPersonId(2l);		
		System.out.println(person2.getDirectorFilms().size());
		//System.out.println(personRepository.findAll().get(0).getActors().get(0).getCharacter());
		
		
		System.out.println("TEST");
		Actor actor = actorRepository.findAll().get(0);
		
		System.out.println(actor.getCharacter());
		
		System.out.println(actor.getFilm().getOriginalTitle());
		
		System.out.println(actor.getPerson().getFullName());
		
		//System.out.println(filmRepository.findFilmByActors(actors.get(0)).get(0).getOriginalTitle());*/
		return null;
	}
}

class ServiceRelation {
	String parent;
	String child;

	public ServiceRelation(String parent, String child) {
		this.parent = parent;
		this.child = child;
	}

	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getChild() {
		return child;
	}
	public void setChild(String child) {
		this.child = child;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceRelation)) return false;
		ServiceRelation that = (ServiceRelation) o;
		return Objects.equals(parent, that.parent) &&
				Objects.equals(child, that.child);
	}

	@Override
	public int hashCode() {

		return Objects.hash(parent, child);
	}
}

class ServiceRelationCardinal extends ServiceRelation {
	final QuantityRule rule;
	public ServiceRelationCardinal(String parent, String child, QuantityRule rule) {
		super(parent, child);
		this.rule = rule;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceRelationCardinal)) return false;
		if (!super.equals(o)) return false;
		ServiceRelationCardinal that = (ServiceRelationCardinal) o;
		return rule == that.rule;
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), rule);
	}
}

enum QuantityRule {
	ZERO_OR_ONE, ONE, ONE_OR_MORE;
	void validate(Quantity quantity) throws Exception {
		switch(this) {
			case ZERO_OR_ONE:
				if(quantity == Quantity.MORE_THAN_ONE) {
					throw new Exception();
				}
				break;
			case ONE:
				if(quantity != Quantity.ONE) {
					throw new Exception();
				}
				break;
			case ONE_OR_MORE:
				if(quantity == Quantity.ZERO) {
					throw new Exception();
				}
				break;
		}
	}
}

enum Quantity {
	ZERO, ONE, MORE_THAN_ONE;

	private Quantity() { }

	Quantity raise() {
		return this == Quantity.ZERO ? Quantity.ONE : Quantity.MORE_THAN_ONE;
	}

	static Quantity getInstance() {
		return Quantity.ZERO;
	}
}
