package rs.gecko.assessment.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;

public class ConfigControllerTest {

	@InjectMocks
	private ConfigController configController;

	@Mock
	private MapService mapService;

	@Mock
	private WeatherService weatherService;


	private MockMvc mocMvc;

	@Before
	public void setupConfigPage() {
		MockitoAnnotations.initMocks(this);

		mocMvc = MockMvcBuilders.standaloneSetup(configController).build();

	}

	@Test
	public void testConfigsPage() throws Exception {

		List<Maps> maps = new ArrayList<>();
		Maps map = new Maps();
		maps.add(map);
		Maps map1 = new Maps();
		maps.add(map1);

		List<Weather> weathers = new ArrayList<>();
		Weather weather = new Weather();
		weathers.add(weather);
		Weather weather1 = new Weather();
		weathers.add(weather1);

		when(mapService.listAll()).thenReturn((List) maps);
		when(weatherService.listAll()).thenReturn((List) weathers);

		mocMvc.perform(get("/configs")).andExpect(status().isOk())
				.andExpect(model().attribute("mapConfigs", hasSize(2)))
				.andExpect(model().attribute("weatherConfigs", hasSize(2)));

	}

}
