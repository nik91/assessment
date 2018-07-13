package rs.gecko.repositories;

import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.security.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String userName);
}
