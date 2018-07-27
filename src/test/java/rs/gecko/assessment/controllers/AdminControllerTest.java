package rs.gecko.assessment.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;


public class AdminControllerTest {

	@Mock
	private CityService cityService;

	@Mock
	private MapService mapService;

	@Mock
	private WeatherService weatherService;


	@InjectMocks
	private AdminController adminController;



	private MockMvc mockMvc;

	@Before
	public void setupAdminTest() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).alwaysDo(print()).build();
	}


	@Test
	@Transactional
	public void testAdminPage() throws Exception {
		List<City> cities = new ArrayList<>();
		City city = new City();
		city.setName("Belgrade,RS");
		cities.add(city);

		City city1 = new City();
		city1.setName("Gornji Milanovac,RS");
		cities.add(city1);

		Weather weather = new Weather();
		weather.setEnabled(true);
		List<Weather> weathers = new ArrayList<>();
		weathers.add(weather);

		Maps map = new Maps();
		map.setEnabled(true);
		List<Maps> maps = new ArrayList<>();
		maps.add(map);

		when(cityService.listAll()).thenReturn((List) cities);
		when(weatherService.listAll()).thenReturn((List) weathers);
		when(mapService.listAll()).thenReturn((List) maps);
		
		mockMvc.perform(get("/admin")).andExpect(status().isOk()).andExpect(view().name("pages/admin"))
				.andExpect(model().attribute("cityDetails", hasSize(2)))

				.andExpect(model().attribute("config", 2))

				.andExpect(model().attribute("mapconfig", 1))

				.andExpect(model().attribute("weatherconfig", 1))

				.andExpect(model().attribute("countofcities", 2))

				.andExpect(model().attribute("activeMapConfig", true))

				.andExpect(model().attribute("activeWetherConfig", true));
	}

}