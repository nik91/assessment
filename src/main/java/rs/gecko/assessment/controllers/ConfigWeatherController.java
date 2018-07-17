package rs.gecko.assessment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.gecko.assessment.services.WeatherService;

@RequestMapping("/configs/Weather")
@Controller
public class ConfigWeatherController {



	@Autowired
	WeatherService weatherService;



	@RequestMapping("/delete/{id}")
	public String configPage(@PathVariable Integer id, Model model) {


		weatherService.delete(id);
		return "redirect:/configs";
	}

}
