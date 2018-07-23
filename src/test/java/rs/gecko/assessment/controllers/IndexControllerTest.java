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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import rs.gecko.assessment.AssessmentApplication;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.services.CityService;

@RunWith(SpringRunner.class)
@Import(AssessmentApplication.class)
public class IndexControllerTest {

	@Mock // Mockito Mock object
	private CityService cityService;

	@InjectMocks // setups up controller, and injects mock objects into it.
	private IndexController indexController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); // initilizes controller and mocks

		// productController.setProductFormToProduct(new ProductFormToProduct());
		// productController.setProductToProductForm(new ProductToProductForm());

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

		// specific Mockito interaction, tell stub to return list of Cities
		when(cityService.listAll()).thenReturn((List) cities); // need to strip
		// generics to keep Mockito happy.

		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("pages/index"))
				.andExpect(model().attribute("cityDetails", hasSize(2)));
	}

	// @Test
	// public void testList() throws Exception{
	//
	// List<Product> products = new ArrayList<>();
	// products.add(new Product());
	// products.add(new Product());
	//
	// //specific Mockito interaction, tell stub to return list of products
	// when(productService.listAll()).thenReturn((List) products); //need to strip
	// generics to keep Mockito happy.
	//
	// mockMvc.perform(get("/product/list"))
	// .andExpect(status().isOk())
	// .andExpect(view().name("product/list"))
	// .andExpect(model().attribute("products", hasSize(2)));
	// }

}