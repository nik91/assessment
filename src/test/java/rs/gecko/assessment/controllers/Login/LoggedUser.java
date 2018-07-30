package rs.gecko.assessment.controllers.Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import rs.gecko.assessment.controllers.LoginController;

public class LoggedUser {

	private MockMvc mockMvc;

	@InjectMocks
	private LoginController loginController;

	@Before
	public void setupAuthentication() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).alwaysDo(print()).build();
	}

	@Test
	public void testLoginForLoggedUser() throws Exception {
		mockMvc.perform(get("/login")).andExpect(redirectedUrl("/admin")).andExpect(view().name("redirect:/admin"));
	}

}
