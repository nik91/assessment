package rs.gecko.assessment.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;


/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Class for populate database by start data.
 */
@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private CityService cityService;

	@Autowired
	private MapService mapService;

	@Autowired
	private WeatherService weatherService;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadCities();
		loadConfig();
	}


	/**
	 * Populate Configuration for Maps and Weather APIs
	 */
	private void loadConfig() {
		Maps map = new Maps();
		map.setEnabled(true);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");
		mapService.saveOrUpdate(map);

		Maps map1 = new Maps();
		map1.setEnabled(false);
		map1.setParametars("?format=json");
		map1.setUrl("https://nominatim.openstreetmap.org/search/");
		mapService.saveOrUpdate(map1);

		Maps map2 = new Maps();
		map2.setEnabled(false);
		map2.setParametars("?format=json");
		map2.setUrl("https://nominatim.openstreetmap.org/search/");
		mapService.saveOrUpdate(map2);

		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");
		weatherService.saveOrUpdate(weather);

		Weather weather1 = new Weather();
		weather1.setEnabled(false);
		weather1.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather1.setParametars("&cnt=3");
		weather1.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");
		weatherService.saveOrUpdate(weather1);

	}

	/**
	 * Populate Cities to database
	 */
	private void loadCities() {

		City city = new City();
		city.setName("Belgrede");
		city.setState("");
		cityService.saveOrUpdate(city);

		City city1 = new City();
		city1.setName("Novi Sad");
		city1.setState("RS");
		cityService.saveOrUpdate(city1);

		City city2 = new City();
		city2.setName("Gornji Milanovac");
		city2.setState("RS");
		cityService.saveOrUpdate(city2);

		City city3 = new City();
		city3.setName("Beograd");
		city3.setState("RS");
		cityService.saveOrUpdate(city3);

	}

}
