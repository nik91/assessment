package rs.gecko.assessment.controllers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.converters.city.CityFormToCity;
import rs.gecko.assessment.converters.city.CityToCityForm;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;

public class CityControllerTest {

	@InjectMocks
	private CityController cityController;

	@Mock
	private CityService cityService;

	@Mock
	private MapService mapService;

	@Mock
	private WeatherService weatherService;

	MockMvc mocMvc;

	@Mock
	private CityToCityForm cityToCityForm;

	@Mock
	private CityFormToCity cityFormToCity;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mocMvc = MockMvcBuilders.standaloneSetup(cityController).alwaysDo(print()).build();

	}

	@Test
	public void openCitiesPage() throws Exception {

		List<City> cities = populateCities();
		Maps map = new Maps();
		map.setEnabled(true);
		Weather weather = new Weather();
		weather.setEnabled(true);

		List<Maps> maps = populateMaps();
		List<Weather> weathers = populateWeather();

		when(cityService.listAll()).thenReturn((List) cities);
		when(weatherService.findEnabledTrue()).thenReturn(weather);
		when(mapService.findEnabledTrue()).thenReturn(map);

		mocMvc.perform(get("/cities")).andExpect(status().isOk()).andExpect(view().name("pages/cities"))
				.andExpect(model().attribute("cityDetails", hasSize(2))).andExpect(model().attribute("configOk", true));

		verify(cityService, times(1)).listAll();
		verify(weatherService, times(1)).findEnabledTrue();
		verify(mapService, times(1)).findEnabledTrue();
	}

	@Test
	public void newCity() throws Exception {

		verifyZeroInteractions(cityService);

		mocMvc.perform(get("/cities/new")).andExpect(status().isOk()).andExpect(view().name("pages/cityform"))
				.andExpect(model().attribute("cityForm", instanceOf(CityForm.class)))
				.andExpect(model().attribute("viewOption", "cityForm.new"));
	}

	@Test
	@Transactional
	public void editCity() throws Exception {
		Integer id = 1;

		List<City> cities = populateCities();

		when(cityService.getById(id)).thenReturn(cities.get(0));

		mocMvc.perform(get("/cities/edit/1")).andExpect(status().isOk()).andExpect(view().name("pages/cityform"))
				.andExpect(model().attribute("cityForm", instanceOf(CityForm.class)))
				.andExpect(model().attribute("cityForm", hasProperty("id", is(1))))
				.andExpect(model().attribute("cityForm", hasProperty("cityName", is("Belgrade"))))
				.andExpect(model().attribute("cityForm", hasProperty("state", is("RS"))))
				.andExpect(model().attribute("viewOption", "cityForm.edit"));
	}

	@Test
	@Transactional
	public void testSaveOrUpdate() throws Exception {

		List<City> cities = populateCities();
		City city = cities.get(0);

		CityDetails cityDetails = new CityDetails();
		cityDetails.setCity(city);
		cityDetails.setTemperature(15.1);
		cityDetails.setLon(15.1);
		cityDetails.setLat(15.1);

		when(cityService.getCityDetails(Matchers.<City>any())).thenReturn(cityDetails);

		mocMvc.perform(
				post("/cities").param("id", "5").param("cityName", city.getName()).param("state", city.getState()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/cities/"));

		mocMvc.perform(post("/cities").param("id", "5").param("cityName", "RS").param("state", city.getState()))
				.andExpect(status().isOk()).andExpect(view().name("pages/cityform"));

	}

	@Test
	public void deleteCity() throws Exception {
		Integer id = 1;

		mocMvc.perform(get("/cities/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/cities"));

		verify(cityService, times(1)).delete(id);

	}

	private List<Weather> populateWeather() {
		List<Weather> weathers = new ArrayList<>();
		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");
		weathers.add(weather);
		return weathers;
	}

	private List<Maps> populateMaps() {
		List<Maps> maps = new ArrayList<>();
		Maps map = new Maps();
		map.setEnabled(true);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");
		maps.add(map);
		return maps;
	}

	private List<City> populateCities() {
		List<City> cities = new ArrayList<>();
		City city1 = new City();
		city1.setId(1);
		city1.setName("Belgrade");
		city1.setState("RS");
		cities.add(city1);
		City city2 = new City();
		city2.setId(2);
		city2.setName("Gornji Milanovac");
		city2.setState("RS");
		cities.add(city2);
		return cities;
	}

}
