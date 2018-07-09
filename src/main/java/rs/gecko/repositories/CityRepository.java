package rs.gecko.repositories;
import org.springframework.data.repository.CrudRepository;

import rs.gecko.assessment.domain.City;

public interface CityRepository extends CrudRepository<City, Integer> {

}
