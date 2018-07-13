package rs.gecko.assessment.services;

import javax.validation.Valid;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;

public interface CityService extends CRUDService<City>{

	CityDetails getCityDetails(City citie);

	City saveOrUpdateCityForm(@Valid CityForm cityForm);

}
