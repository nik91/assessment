package rs.gecko.assessment.services.reposervices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.externalservices.weather.WeatherParam;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;
import rs.gecko.repositories.CityRepository;

@Service
public class CityServiceRepoImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private MapService mapService;
	@Autowired
	private WeatherService weatherService;

	@Override
	public List<?> listAll() {
		List<City> cities = new ArrayList<>();
		cityRepository.findAll().forEach(cities::add);
		return cities;
	}

	@Override
	public Optional<City> getById(Integer id) {
		return cityRepository.findById(id);
	}

	@Override
	public City saveOrUpdate(City domainObject) {
		return cityRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {

	}

	@Override
	public CityDetails getCityDetails(City citie) {
		CityDetails cityDetail = new CityDetails();

		cityDetail.setCity(citie.getName());

		Location location = mapService.getData(citie.getName());
		cityDetail.setLat(round(location.getLat(), 5));
		cityDetail.setLon(round(location.getLon(), 5));

		WeatherParam weatherParam = weatherService.getData(citie.getName());
		cityDetail.setTemperature(round(weatherParam.getMain().getTemp() - 273, 2));

		return cityDetail;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
