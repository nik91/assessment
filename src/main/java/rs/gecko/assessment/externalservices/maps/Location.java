package rs.gecko.assessment.externalservices.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 *         Location is map object for input JSON from Map API Location contains
 *         information about City Latitude and Longitude
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {


	/**
	 * City Latitude
	 */
	private Double lat;
	/**
	 * City Longitude
	 */
	private Double lon;
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLoc(Double lon) {
		this.lon = lon;
	}

}
