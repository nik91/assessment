package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.security.User;

public interface UserService extends CRUDService<User> {

	User findByUsername(String userName);
}
