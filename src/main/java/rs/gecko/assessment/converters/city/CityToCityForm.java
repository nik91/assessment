package rs.gecko.assessment.converters.city;

import org.springframework.core.convert.converter.Converter;
import org.springframework.test.annotation.Commit;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.domain.City;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Convert City to City Form
 */
@Commit
public class CityToCityForm implements Converter<City, CityForm> {

	@Override
	public CityForm convert(City source) {
		CityForm cityform = new CityForm();

		cityform.setCityName(source.getName());
		cityform.setState(source.getState());
		cityform.setId(source.getId());

		return cityform;
	}

}
