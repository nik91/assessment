package rs.gecko.repositories;

import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
