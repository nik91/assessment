package rs.gecko.assessment.controllers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.WeatherService;

public class ConfigWeatherControllerTest {

	@InjectMocks
	private ConfigWeatherController configWeatherController;

	@Mock
	private WeatherService weatherService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(configWeatherController).alwaysDo(print()).build();

	}

	@Test
	public void newWeather() throws Exception {

		mockMvc.perform(get("/configs/Weather/new")).andExpect(status().isOk()).andExpect(view().name("pages/apiform"))
				.andExpect(model().attribute("apiForm", instanceOf(ApiForm.class)))
				.andExpect(model().attribute("ConfigActive", "active"))
				.andExpect(model().attribute("viewOption", "apiForm.viewOption.new"))
				.andExpect(model().attribute("formType", "Weather"))
				.andExpect(model().attribute("formAction", "/configs/Weather/update"))
				.andExpect(model().attribute("formTitle", "apiForm.weather"));

	}

	@Test
	@Transactional
	public void editWeatherConfig() throws Exception {
		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setId(1);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");

		when(weatherService.getById(1)).thenReturn(weather);

		mockMvc.perform(get("/configs/Weather/edit/1")).andExpect(status().isOk())
				.andExpect(view().name("pages/apiform"))
				.andExpect(model().attribute("apiForm", instanceOf(ApiForm.class)))
				.andExpect(model().attribute("apiForm", hasProperty("id", is(1))))
				.andExpect(model().attribute("apiForm", hasProperty("apiKey", is("5cf206b12a8b4ca9e171d70bdb6be856"))))
				.andExpect(model().attribute("apiForm", hasProperty("parametars", is("&cnt=1"))))
				.andExpect(model().attribute("apiForm",
						hasProperty("url", is("http://api.openweathermap.org/data/2.5/weather?q="))))
				.andExpect(model().attribute("ConfigActive", "active"))
				.andExpect(model().attribute("viewOption", "apiForm.viewOption.edit"))
				.andExpect(model().attribute("formType", "Weather"))
				.andExpect(model().attribute("formAction", "/configs/Weather/update"))
				.andExpect(model().attribute("formTitle", "apiForm.weather"));
	}

	@Test
	public void deleteWeatherConfig() throws Exception {
		Integer id = 1;

		mockMvc.perform(get("/configs/Weather/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/configs"));

		verify(weatherService, times(1)).delete(id);

	}

	@Test
	@Transactional
	public void testSaveOrUpdate() throws Exception {

		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setId(1);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("&cnt=1");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");

		// when(weatherService.saveOrUpdate((Matchers.<Weather>any())).thenReturn(weather);

		mockMvc.perform(post("/configs/Weather/update").param("id", "5").param("url", weather.getUrl())
				.param("apiKey", weather.getApiKey()).param("parametars", weather.getParametars()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/configs"));

		mockMvc.perform(post("/configs/Weather/update").param("id", "5").param("url", "something that is not url")
				.param("apiKey", weather.getApiKey()).param("parametars", weather.getParametars()))
				.andExpect(status().isOk()).andExpect(view().name("pages/apiform"));

	}
}
