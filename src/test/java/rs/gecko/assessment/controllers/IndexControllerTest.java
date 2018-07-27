package rs.gecko.assessment.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.services.CityService;


public class IndexControllerTest {

	@Mock
	private CityService cityService;

	@InjectMocks
	private IndexController indexController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
	}

	@Test
	public void getIndexPage() throws Exception {
		List<City> cities = new ArrayList<>();
		City city = new City();
		city.setName("Belgrade,RS");
		cities.add(city);

		City city1 = new City();
		city1.setName("Gornji Milanovac,RS");
		cities.add(city1);

		when(cityService.listAll()).thenReturn((List) cities);

		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("pages/index"))
				.andExpect(model().attribute("cityDetails", hasSize(2)));
	}

}