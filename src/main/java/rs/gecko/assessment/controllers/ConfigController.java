package rs.gecko.assessment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;

@Controller
public class ConfigController {

	@Autowired
	MapService mapService;

	@Autowired
	WeatherService weatherService;

	@RequestMapping("/configs")
	public String configPage(Model model) {

		List<Maps> maps = (List<Maps>) mapService.listAll();

		List<Weather> weathers = (List<Weather>) weatherService.listAll();

		model.addAttribute("mapConfigs", maps);
		model.addAttribute("weatherConfigs", weathers);
		model.addAttribute("ConfigActive", "active");

		return "pages/configs";
	}



}
