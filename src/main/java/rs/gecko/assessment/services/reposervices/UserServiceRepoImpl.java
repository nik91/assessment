package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.gecko.assessment.domain.security.User;
import rs.gecko.assessment.services.UserService;
import rs.gecko.repositories.UserRepository;

@Service
public class UserServiceRepoImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<?> listAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public Optional<User> getById(Integer id) {

		return userRepository.findById(id);
	}

	@Override
	public User saveOrUpdate(User domainObject) {
		// domainObject.setPassword(bCryptPasswordEncoder.encode(domainObject.getPassword()));

		return userRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);

	}

	@Override
	public User findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

}
