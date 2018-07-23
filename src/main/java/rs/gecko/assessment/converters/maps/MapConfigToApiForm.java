package rs.gecko.assessment.converters.maps;

import org.springframework.core.convert.converter.Converter;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.domain.api.Maps;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Convert Map Configuration to API Form
 */
public class MapConfigToApiForm implements Converter<Maps, ApiForm> {

	@Override
	public ApiForm convert(Maps map) {

		ApiForm apiForm = new ApiForm();

		apiForm.setId(map.getId());
		apiForm.setUrl(map.getUrl());
		apiForm.setParametars(map.getParametars());
		apiForm.setEnabled(map.isEnabled());
		apiForm.setApiKey("");
		return apiForm;
	}

}
