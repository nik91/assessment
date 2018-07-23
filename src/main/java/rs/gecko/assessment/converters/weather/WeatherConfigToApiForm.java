package rs.gecko.assessment.converters.weather;

import org.springframework.core.convert.converter.Converter;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.domain.api.Weather;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Convert Weather Configuration to API Form
 */
public class WeatherConfigToApiForm implements Converter<Weather, ApiForm> {

	@Override
	public ApiForm convert(Weather weather) {

		ApiForm apiForm = new ApiForm();

		apiForm.setId(weather.getId());
		apiForm.setUrl(weather.getUrl());
		apiForm.setParametars(weather.getParametars());
		apiForm.setEnabled(weather.isEnabled());
		apiForm.setApiKey(weather.getApiKey());

		return apiForm;
	}

}
