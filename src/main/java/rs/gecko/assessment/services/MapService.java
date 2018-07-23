package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;


/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
public interface MapService extends CRUDService<Maps> {
	/**
	 * Return object Location for City
	 * 
	 * @param city
	 *            Object of City
	 * @return object Location if there is information about requested city
	 */
	public Location getData(City city);

	/**
	 * Return an Map config that is active at the time
	 * 
	 * @return Maps config that is active if it exist in list of weather configs
	 */
	public Maps findEnabledTrue();
}
