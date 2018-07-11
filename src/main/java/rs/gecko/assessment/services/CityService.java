package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;

public interface CityService extends CRUDService<City>{

	CityDetails getCityDetails(City citie);

}
