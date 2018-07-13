package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.gecko.assessment.domain.security.Role;
import rs.gecko.assessment.services.RoleService;
import rs.gecko.repositories.RoleRepository;

@Service
public class RoleServiceRepoImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<?> listAll() {

		List<Role> roles = new ArrayList<>();
		roleRepository.findAll().forEach(roles::add);
		return roles;
	}

	@Override
	public Optional<Role> getById(Integer id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role saveOrUpdate(Role domainObject) {
		return roleRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {

		roleRepository.deleteById(id);
	}

}
