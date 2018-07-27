package rs.gecko.assessment.services.reposervices;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import rs.gecko.assessment.AssessmentApplication;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.externalservices.weather.WeatherParam;
import rs.gecko.assessment.services.WeatherService;

@RunWith(SpringRunner.class)
@Import(AssessmentApplication.class)
public class WeatherServiceRepoImplTest {

	@Autowired
	private WeatherService weatherService;

	@Test
	@Transactional
	public void testListWeatherConfigs() {
		List<Weather> weathers = (List<Weather>) weatherService.listAll();

		assert weathers.size() == 2;

	}

	@Test
	@Transactional
	public void testDeleteWeatherConfig() {

		List<Weather> weathers = (List<Weather>) weatherService.listAll();

		weatherService.delete(weathers.get(0).getId());

		List<Weather> citiesAfterDelete = (List<Weather>) weatherService.listAll();

		assert citiesAfterDelete.size() == 1;

	}

	@Test
	@Transactional
	public void testSaveWeatherConfig() {

		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");
		Weather savedWeather = weatherService.saveOrUpdate(weather);

		assert savedWeather.getId() != null;
	}

	@Test
	@Transactional
	public void testFindWeatherConfigById() {

		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");

		Weather saveWeather = weatherService.saveOrUpdate(weather);

		Weather savedWeather = weatherService.getById(saveWeather.getId());

		assert saveWeather == savedWeather;
	}

	@Test
	@Transactional
	public void setEnabledWeatherConfig() {
		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");

		Weather weatherSaved = weatherService.saveOrUpdate(weather);

		Weather weatherEnabled = weatherService.findEnabledTrue();

		assert weatherSaved == weatherEnabled;

		weatherSaved.setEnabled(false);
		weatherService.saveOrUpdate(weatherSaved);

		assert weatherService.findEnabledTrue() == null;

	}

	@Test
	@Transactional
	public void getParamForCity() {

		City city = new City();
		city.setName("Beograd");

		WeatherParam weatherParam = weatherService.getData(city);

		assertNull(weatherParam.getMain().getTemp());

		City city1 = new City();
		city1.setName("Belgrade,RS");

		WeatherParam weatherParam1 = weatherService.getData(city1);

		assertNotNull(weatherParam1.getMain().getTemp());

	}

}
