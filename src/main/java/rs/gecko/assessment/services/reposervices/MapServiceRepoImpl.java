package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.services.MapService;
import rs.gecko.repositories.MapReposetory;

@Service
public class MapServiceRepoImpl implements MapService {

	@Autowired
	private MapReposetory mapRepository;

	private final RestTemplate restTemplate;

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
		return mapRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {
		mapRepository.deleteById(id);

	}

	@Override
	public Location getData(City city) {
		Maps mapsApi = findEnabled(true);
		// System.out.println(mapsApi.toString());
		Location[] location = new Location[1];

		if (city != null && mapsApi != null) {

			try {
				location = this.restTemplate.getForObject(mapsApi.toString(), Location[].class, city.toString());
			} catch (Exception e) {
				return location[0];
			}

		}

		if (location.length < 1) {
			return null;
		}
		return location[0];
	}

	@Override
	public Maps findEnabled(boolean enabled) {
		return mapRepository.findByEnabled(enabled);
	}

}
