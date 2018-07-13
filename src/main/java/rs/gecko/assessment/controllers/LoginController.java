package rs.gecko.assessment.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {



	@RequestMapping("login")
	public String loginForm() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}


		return "login";
	}

	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	// public String login(Model model, String error, String logout) {
	// if (error != null)
	// model.addAttribute("error", "Your username and password is invalid.");
	//
	// if (logout != null)
	// model.addAttribute("message", "You have been logged out successfully.");
	//
	// return "login1";
	// }

}
