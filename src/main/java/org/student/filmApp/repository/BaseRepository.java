package org.student.filmApp.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

		long count();

		void delete(T entity);

		void deleteAll(Iterable<? extends T> entities);

		void deleteById(ID id);

		boolean existsById(ID id);

		Set<T> findAll();

		Set<T> findAllById(Iterable<ID> ids);

		Optional<T> findById(ID id);

		<S extends T> S save(S entity);

		<S extends T> Iterable<S> saveAll(Iterable<S> entities);

}
