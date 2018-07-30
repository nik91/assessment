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
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.services.MapService;

public class ConfigMapControllerTest {

	@InjectMocks
	private ConfigMapController configMapController;

	@Mock
	private MapService mapService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(configMapController).alwaysDo(print()).build();

	}

	@Test
	public void newMapConfig() throws Exception {

		mockMvc.perform(get("/configs/Map/new")).andExpect(status().isOk()).andExpect(view().name("pages/apiform"))
				.andExpect(model().attribute("apiForm", instanceOf(ApiForm.class)))
				.andExpect(model().attribute("ConfigActive", "active"))
				.andExpect(model().attribute("viewOption", "apiForm.viewOption.new"))
				.andExpect(model().attribute("formType", "Map"))
				.andExpect(model().attribute("formAction", "/configs/Map/update"))
				.andExpect(model().attribute("formTitle", "apiForm.maps"));

	}

	@Test
	@Transactional
	public void editMapConfig() throws Exception {
		Maps map = new Maps();
		map.setEnabled(true);
		map.setId(1);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");

		when(mapService.getById(1)).thenReturn(map);

		mockMvc.perform(get("/configs/Map/edit/1")).andExpect(status().isOk())
				.andExpect(view().name("pages/apiform"))
				.andExpect(model().attribute("apiForm", instanceOf(ApiForm.class)))
				.andExpect(model().attribute("apiForm", hasProperty("id", is(1))))
				.andExpect(model().attribute("apiForm", hasProperty("parametars", is("?format=json"))))
				.andExpect(model().attribute("apiForm",
						hasProperty("url", is("https://nominatim.openstreetmap.org/search/"))))
				.andExpect(model().attribute("ConfigActive", "active"))
				.andExpect(model().attribute("viewOption", "apiForm.viewOption.edit"))
				.andExpect(model().attribute("formType", "Map"))
				.andExpect(model().attribute("formAction", "/configs/Map/update"))
				.andExpect(model().attribute("formTitle", "apiForm.maps"));
	}

	@Test
	public void deleteWeatherConfig() throws Exception {
		Integer id = 1;

		mockMvc.perform(get("/configs/Map/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/configs"));

		verify(mapService, times(1)).delete(id);

	}

	@Test
	@Transactional
	public void testSaveOrUpdate() throws Exception {

		Maps map = new Maps();
		map.setEnabled(true);
		map.setId(1);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");

		// when(weatherService.saveOrUpdate((Matchers.<Weather>any())).thenReturn(weather);

		mockMvc.perform(post("/configs/Map/update").param("id", "5").param("url", map.getUrl()).param("parametars",
				map.getParametars())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/configs"));

		mockMvc.perform(post("/configs/Map/update").param("id", "5").param("url", "something that is not url")
				.param("parametars", map.getParametars())).andExpect(status().isOk())
				.andExpect(view().name("pages/apiform"));

	}

}
