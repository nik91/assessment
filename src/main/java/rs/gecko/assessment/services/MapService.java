package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;

public interface MapService extends CRUDService<Maps> {

	public Location getData(String city);
	public Maps findEnabled(boolean enabled);
}
