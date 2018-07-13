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

@Controller
public class IndexController {
	@Autowired
	private CityService cityService;


	/*
	 * @RequestMapping("login") public String loginForm(){ return "login"; }
	 */

	@RequestMapping("/cities")
	public String sities() {
		return "cities";
	}

	@RequestMapping("/")
	public String index(Model model) {
		List<CityDetails> citiesDetails = new ArrayList<CityDetails>();

		
		List<City> cities = (List<City>) cityService.listAll();

		cities.forEach(citie -> {
			CityDetails cityDetail = cityService.getCityDetails(citie);

			citiesDetails.add(cityDetail);

		});
		
		model.addAttribute("cityDetails", citiesDetails);

		return "pages/index";
	}
}
