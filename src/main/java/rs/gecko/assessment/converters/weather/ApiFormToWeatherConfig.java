package rs.gecko.assessment.converters.weather;

import org.springframework.core.convert.converter.Converter;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.domain.api.Weather;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Convert API Form to Weather Configuration
 */
public class ApiFormToWeatherConfig implements Converter<ApiForm, Weather> {

	@Override
	public Weather convert(ApiForm apiForm) {

		Weather weather = new Weather();

		weather.setId(apiForm.getId());
		weather.setUrl(apiForm.getUrl());
		weather.setParametars(apiForm.getParametars());
		weather.setApiKey(apiForm.getApiKey());
		weather.setEnabled(apiForm.isEnabled());

		return weather;
	}

}
