package rs.gecko.assessment.controllers.Login;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnonimusUserTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setupAuthentication() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).alwaysDo(print()).build();

	}

	@Test
	public void loginAvailableForAll() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}

	@Test
	public void testAdminPageForUnlogedUser() throws Exception {
		mockMvc.perform(get("/admin")).andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void testCitiesPageForUnlogedUser() throws Exception {
		mockMvc.perform(get("/cities")).andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void testConfigsPageForUnlogedUser() throws Exception {
		mockMvc.perform(get("/configs")).andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void testIndexUnlogedUser() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

}
