package rs.gecko.assessment.services.reposervices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.gecko.assessment.controllers.LogAcitveConfigsController;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.LogActiveConfigs;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.externalservices.weather.WeatherParam;
import rs.gecko.assessment.services.WeatherService;
import rs.gecko.repositories.WeatherRepository;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 */
@Service
public class WeatherServiceRepoImpl implements WeatherService {

	@Autowired
	private WeatherRepository weatherRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherServiceRepoImpl.class);

	@Autowired
	private LogAcitveConfigsController logActiveConfigsController;

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
	public Weather getById(Integer id) {

		return weatherRepository.findById(id).get();
	}

	@Override
	public Weather saveOrUpdate(Weather domainObject) {

		if (domainObject.isEnabled()) {
			restartEnabled();
			domainObject.setEnabled(true);

		}

		Weather weather = weatherRepository.save(domainObject);
		if (weather.isEnabled()) {
			logActiveConfigsController.addLog(new LogActiveConfigs(weather.getId(), weather.getUrl(),
					weather.getParametars(), weather.getApiKey(), new Date()));
		}

		return weather;
	}

	private void restartEnabled() {
		List<Weather> weathers = (List<Weather>) listAll();
		weathers.forEach(weather -> {

			if (weather.isEnabled()) {
				weather.setEnabled(false);
				saveOrUpdate(weather);
			}

		});

	}

	@Override
	public void delete(Integer id) {
		weatherRepository.deleteById(id);

	}

	@Override
	public WeatherParam getData(City city) {

		Weather WeatherApi = findEnabledTrue();
		LOGGER.debug("Weather Api: {}", WeatherApi == null ? WeatherApi : WeatherApi.toString());
		WeatherParam watherParam = new WeatherParam();

		if (city != null && WeatherApi != null) {
			try {
				watherParam = this.restTemplate.getForObject(WeatherApi.toString(), WeatherParam.class,
						city.getCityAndState());
				LOGGER.debug("Temperature for City: " + city.getName() + " , is: " + watherParam.getMain().getTemp());
			} catch (Exception e) {

				LOGGER.warn(
						e.toString() + "----- There is no Api Config or City Name is not found on Api Server. -----");
				return watherParam;
			}

		}

		return watherParam;
	}

	@Override
	public Weather findEnabledTrue() {
		return weatherRepository.findByEnabledTrue();
	}

}
