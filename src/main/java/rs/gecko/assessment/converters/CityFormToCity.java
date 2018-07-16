package rs.gecko.assessment.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.domain.City;

@Component
public class CityFormToCity implements Converter<CityForm, City> {

	@Override
	public City convert(CityForm cityForm) {
		City city = new City();
		city.setId(cityForm.getId());
		city.setName(cityForm.getCityName());
		city.setState(cityForm.getState());
		return city;
	}

}
