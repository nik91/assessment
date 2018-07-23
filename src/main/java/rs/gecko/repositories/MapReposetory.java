package rs.gecko.repositories;

import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.api.Maps;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
public interface MapReposetory extends CrudRepository<Maps, Integer> {

	/**
	 * Return an Map config that is active at the time
	 * 
	 * @return Maps config that is active if it exist in list of weather configs
	 */
	Maps findByEnabledTrue();

}
