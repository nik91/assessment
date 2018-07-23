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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import rs.gecko.assessment.AssessmentApplication;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.services.CityService;

@RunWith(SpringRunner.class)
@Import(AssessmentApplication.class)
@SpringBootTest
public class AdminControllerTest {



	@Mock // Mockito Mock object
	private CityService cityService;

	@InjectMocks // setups up controller, and injects mock objects into it.
	private AdminController adminController;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		// mockMvc = MockMvcBuilders.webAppContextSetup(context)
		// .defaultRequest(get("/").with(user("karec91").password("nikola91").roles("ADMIN")))
		// .apply(springSecurity()).build();
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
		// .defaultRequest(get("/").with(user("karec91").password("nikola91").roles("ADMIN")))
		// .apply(springSecurity()).build();
	}


	@Test
	//@WithMockUser // (username = "karec91", password = "nikola91", roles = "ADMIN")
	// @Transactional
	public void getAdminpage() throws Exception {
		List<City> cities = new ArrayList<>();
		City city = new City();
		city.setName("Belgrade,RS");
		cities.add(city);

		City city1 = new City();
		city1.setName("Gornji Milanovac,RS");
		cities.add(city1);

		when(cityService.listAll()).thenReturn((List) cities);

//		mockMvc.perform(get("/admin").with(user("karec91").roles("ADMIN")))
//				.andExpect(status().isNotFound());
		
		
		mockMvc.perform(get("/admin")).andExpect(status().isOk()).andExpect(view().name("pages/admin"))
				.andExpect(model().attribute("cityDetails", hasSize(2)));
	}

}