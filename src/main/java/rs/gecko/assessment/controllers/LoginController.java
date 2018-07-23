package rs.gecko.assessment.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
@Controller
public class LoginController {



	/**
	 * Login page for allowing user to open Admin panel
	 * 
	 * @return login page for user that is not logged in. If already logged in user
	 *         is redirected to Admin page
	 */
	@RequestMapping("login")
	public String loginForm() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/admin";
		}


		return "login";
	}

}
