package rs.gecko.assessment.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.services.CityService;

@Controller
public class CityController {

	@Autowired
	private CityService cityService;


	@RequestMapping("/cities")
	public String cities(Model model) {

		List<CityDetails> citiesDetails = new ArrayList<CityDetails>();

		List<City> cities = (List<City>) cityService.listAll();

		cities.forEach(citie -> {
			CityDetails cityDetail = cityService.getCityDetails(citie);

			citiesDetails.add(cityDetail);

		});

		model.addAttribute("cityDetails", citiesDetails);

		return "pages/cities";
	}

	@RequestMapping("/cities/new")
	public String newProduct(Model model) {
		model.addAttribute("productForm", new CityForm());
		return "pages/cities";
	}

	@RequestMapping(value = "/cities", method = RequestMethod.POST)
	public String saveOrUpdateProduct(@Valid CityForm cityForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "product/productform";
		}

		City city = cityService.saveOrUpdateCityForm(cityForm);

		return "redirect:/cities/city/" + city.getId();
	}

	@RequestMapping("/cities/edit")
	public String cityForm(Model model) {

		return "pages/cityform";
	}
}
