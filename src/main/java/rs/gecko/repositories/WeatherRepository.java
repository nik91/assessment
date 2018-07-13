package rs.gecko.repositories;

import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.api.Weather;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {

	Weather findByEnabled(boolean enabled);

}
