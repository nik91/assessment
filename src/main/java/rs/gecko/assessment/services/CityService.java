package rs.gecko.assessment.services;

import javax.validation.Valid;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 */
public interface CityService extends CRUDService<City>{

	/**
	 * Get City and return details about that City (Weather and Map information)
	 * 
	 * @param citie
	 *            that represent City object
	 * @return CityDetails (Weather and Map information)
	 */
	CityDetails getCityDetails(City citie);

	/**
	 * Save cityForm in database like City object
	 * 
	 * @param cityForm
	 * @return object City that is created from cityForm and stored in database
	 */
	City saveOrUpdateCityForm(@Valid CityForm cityForm);

}
