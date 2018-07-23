package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.externalservices.weather.WeatherParam;


/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
public interface WeatherService extends CRUDService<Weather> {

	/**
	 * Return object WeatherParam for City
	 * 
	 * @param city
	 *            Object of City
	 * @return object WeatherParam if there is information about requested city
	 */
	public WeatherParam getData(City city);

	/**
	 * Return an Weather config that is active at the time
	 * 
	 * @return Weather config that is active if it exist in list of weather configs
	 */
	public Weather findEnabledTrue();
}
