package rs.gecko.repositories;

import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.api.Maps;

public interface MapReposetory extends CrudRepository<Maps, Integer> {

	Maps findByEnabled(boolean enabled);

}
