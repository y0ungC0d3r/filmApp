package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
}
