package rs.gecko.repositories;

import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.api.Weather;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
public interface WeatherRepository extends CrudRepository<Weather, Integer> {

	/**
	 * Return an Weather config that is active at the time
	 * 
	 * @return Weather config that is active if it exist in list of weather configs
	 */
	Weather findByEnabledTrue();

}
