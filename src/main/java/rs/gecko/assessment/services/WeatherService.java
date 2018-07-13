package rs.gecko.assessment.services;

import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.externalservices.weather.WeatherParam;

public interface WeatherService extends CRUDService<Weather> {

	public WeatherParam getData(String city);
	public Weather findEnabled(boolean enabled);
}
