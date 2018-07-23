package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.services.MapService;
import rs.gecko.repositories.MapReposetory;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 */
@Service
public class MapServiceRepoImpl implements MapService {

	@Autowired
	private MapReposetory mapRepository;

	private final RestTemplate restTemplate;

	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherServiceRepoImpl.class);

	public MapServiceRepoImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<?> listAll() {

		List<Maps> maps = new ArrayList<>();
		mapRepository.findAll().forEach(maps::add);
		return maps;
	}

	@Override
	public Maps getById(Integer id) {
		return mapRepository.findById(id).get();
	}

	@Override
	public Maps saveOrUpdate(Maps domainObject) {

		if (domainObject.isEnabled()) {
			restartEnabled();
			domainObject.setEnabled(true);
		}

		return mapRepository.save(domainObject);
	}

	private void restartEnabled() {
		List<Maps> maps = (List<Maps>) listAll();
		maps.forEach(map -> {

			if (map.isEnabled()) {
				map.setEnabled(false);
				saveOrUpdate(map);
			}

		});

	}

	@Override
	public void delete(Integer id) {
		mapRepository.deleteById(id);

	}

	@Override
	public Location getData(City city) {
		Maps mapsApi = findEnabledTrue();

		LOGGER.debug(mapsApi.toString());
		Location[] location = new Location[1];

		if (city != null && mapsApi != null) {

			try {
				location = this.restTemplate.getForObject(mapsApi.toString(), Location[].class, city.getCityAndState());
			} catch (Exception e) {

				LOGGER.warn(e.toString());
				return location[0];
			}

		}

		if (location.length < 1) {
			return null;
		}
		LOGGER.debug(
				"Location for city: " + city.getName() + " is: " + location[0].getLat() + "; " + location[0].getLon());
		return location[0];
	}

	@Override
	public Maps findEnabledTrue() {
		return mapRepository.findByEnabledTrue();
	}

}
