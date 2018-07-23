package rs.gecko.assessment.externalservices.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherParam {

	private int id;
	private MainWeather main = new MainWeather();

	
	
	public MainWeather getMain() {
		return main;
	}

	public void setMain(MainWeather main) {
		this.main = main;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}
