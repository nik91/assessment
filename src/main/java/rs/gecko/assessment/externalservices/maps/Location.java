package rs.gecko.assessment.externalservices.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {


	private Double lat;
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
