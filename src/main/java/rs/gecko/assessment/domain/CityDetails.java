package rs.gecko.assessment.domain;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Collection of City information (Object City and information about
 *         City: Temperature, Latitude, Longitude)
 */
public class CityDetails {

	private City city;
	/**
	 * Temperature get form Weather API
	 */
	private Double temperature;
	/**
	 * Latitude get form Map API
	 */
	private Double lat;
	/**
	 * Longitude get form Map API
	 */
	private Double lon;

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

}
