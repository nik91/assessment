package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.externalservices.weather.WeatherParam;
import rs.gecko.assessment.services.WeatherService;
import rs.gecko.repositories.WeatherRepository;

@Service
public class WeatherServiceRepoImpl implements WeatherService {

	@Autowired
	private WeatherRepository weatherRepository;

	private final RestTemplate restTemplate;

	public WeatherServiceRepoImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<?> listAll() {
		List<Weather> weathers = new ArrayList<>();
		weatherRepository.findAll().forEach(weathers::add);
		return weathers;
	}

	@Override
	public Optional<Weather> getById(Integer id) {

		return weatherRepository.findById(id);
	}

	@Override
	public Weather saveOrUpdate(Weather domainObject) {

		return weatherRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {
		weatherRepository.deleteById(id);

	}

	@Override
	public WeatherParam getData(String city) {

		Weather WeatherApi = findEnabled(true);
		System.out.println(WeatherApi.toString());
		WeatherParam watherParam = new WeatherParam();

		if (city != null) {
			watherParam = this.restTemplate.getForObject(WeatherApi.toString(), WeatherParam.class, city);
			System.out.println(watherParam.getId() + " " + watherParam.getMain().getTemp());

		}

		return watherParam;
	}

	@Override
	public Weather findEnabled(boolean enabled) {
		return weatherRepository.findByEnabled(enabled);
	}

}
