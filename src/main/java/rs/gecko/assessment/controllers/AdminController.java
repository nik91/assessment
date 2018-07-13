package rs.gecko.assessment.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;

@Controller
public class AdminController {

	@Autowired
	private CityService cityService;
	@Autowired
	private MapService mapService;
	@Autowired
	private WeatherService weatherService;

	@RequestMapping("/admin")
	public String adminPage(Model model) {

		List<CityDetails> citiesDetails = new ArrayList<CityDetails>();

		List<City> cities = (List<City>) cityService.listAll();

		cities.forEach(citie -> {
			CityDetails cityDetail = cityService.getCityDetails(citie);

			citiesDetails.add(cityDetail);

		});
		Integer noOfConfiguration = noOfConfig();
		System.out.println(noOfConfiguration);

		model.addAttribute("cityDetails", citiesDetails);
		model.addAttribute("config", mapService.listAll().size() + weatherService.listAll().size());
		model.addAttribute("mapconfig", mapService.listAll().size());
		model.addAttribute("weatherconfig", weatherService.listAll().size());
		model.addAttribute("countofcities", cities.size());
		model.addAttribute("activeMapConfig", mapService.findEnabled(true) == null);
		model.addAttribute("activeWetherConfig", weatherService.findEnabled(true) == null);

		return "pages/admin";
	}

	private Integer noOfConfig() {

		return mapService.listAll().size() + weatherService.listAll().size();

	}

}
