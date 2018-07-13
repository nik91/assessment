package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.services.MapService;
import rs.gecko.repositories.MapReposetory;

@Service
public class MapServiceRepoImpl implements MapService{

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
	public Optional<Maps> getById(Integer id) {
		return mapRepository.findById(id);
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
	public Location getData(String city) {
		Maps mapsApi = findEnabled(true);
		System.out.println(mapsApi.toString());
		Location[] location = new Location[1];

		if (city != null) {
			location = this.restTemplate.getForObject(mapsApi.toString(), Location[].class, city);
			//System.out.println(locations.getLocationss().get(0).getLat()+ " " + location.getLocationss().get(0).getLoc());

		}

		return location[0];
	}

	@Override
	public Maps findEnabled(boolean enabled) {
		return mapRepository.findByEnabled(enabled);
	}

}
