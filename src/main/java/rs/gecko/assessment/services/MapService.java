package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;

public interface MapService extends CRUDService<Maps> {

	public Location getData(City city);
	public Maps findEnabled(boolean enabled);
}
