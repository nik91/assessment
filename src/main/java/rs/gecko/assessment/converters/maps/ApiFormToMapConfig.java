package rs.gecko.assessment.converters.maps;

import org.springframework.core.convert.converter.Converter;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.domain.api.Maps;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Convert API Form to Map Configuration
 */
public class ApiFormToMapConfig implements Converter<ApiForm, Maps> {

	@Override
	public Maps convert(ApiForm apiForm) {

		Maps map = new Maps();

		map.setId(apiForm.getId());
		map.setUrl(apiForm.getUrl());
		map.setParametars(apiForm.getParametars());
		map.setEnabled(apiForm.isEnabled());

		return map;
	}

}
