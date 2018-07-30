package rs.gecko.assessment.services.reposervices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.converters.city.CityFormToCity;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.externalservices.weather.WeatherParam;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;
import rs.gecko.repositories.CityRepository;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 */
@Service
public class CityServiceRepoImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private MapService mapService;
	@Autowired
	private WeatherService weatherService;

	@Autowired
	private CityFormToCity cityFormToCity;

	private final static Logger LOGGER = LoggerFactory.getLogger(CityServiceRepoImpl.class);

	@Override
	public List<?> listAll() {
		List<City> cities = new ArrayList<>();
		cityRepository.findAll().forEach(cities::add);
		return cities;
	}

	@Override
	public City getById(Integer id) {
		return cityRepository.findById(id).get();
	}

	@Override
	public City saveOrUpdate(City domainObject) {
		return cityRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {

		cityRepository.deleteById(id);
	}


	@Override
	public CityDetails getCityDetails(City citie) {
		CityDetails cityDetail = new CityDetails();

		cityDetail.setCity(citie);

		Location location = mapService.getData(citie);
		if (location != null) {
			cityDetail.setLat(round(location.getLat(), 5));
			cityDetail.setLon(round(location.getLon(), 5));
		}
		WeatherParam weatherParam = weatherService.getData(citie);
		if (weatherParam.getMain().getTemp() != null) {
			cityDetail.setTemperature(round(weatherParam.getMain().getTemp() - 273, 2));
		}
		return cityDetail;
	}

	/**
	 * Method for convert doubles to doubles with rounding
	 * 
	 * @param value
	 *            input double
	 * @param places
	 *            input number of places for rounding
	 * @return rounded double
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		LOGGER.debug("Rounded nubmer of: " + value + ", to: " + places + " number of places, is: " + bd);
		return bd.doubleValue();
	}


	@Override
	public City saveOrUpdateCityForm(@Valid CityForm cityForm) {
		City city = cityFormToCity.convert(cityForm);
		return saveOrUpdate(city);
	}
}
