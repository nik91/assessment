package rs.gecko.assessment.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rs.gecko.assessment.commands.CityForm;
import rs.gecko.assessment.converters.CityFormToCity;
import rs.gecko.assessment.converters.CityToCityForm;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.services.CityService;

@Controller
public class CityController {

	@Autowired
	private CityService cityService;

	@Autowired
	private CityFormToCity cityFormToCity;

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
		model.addAttribute("cityForm", new CityForm());
		return "pages/cityform";
	}

	@RequestMapping(value = "/cities", method = RequestMethod.POST)
	public String saveOrUpdateProduct(@Valid CityForm cityForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "cities/cityform";
		}

		City city = cityFormToCity.convert(cityForm);
		CityDetails cityDetails = cityService.getCityDetails(city);

		if (cityDetails.getLat() == null || cityDetails.getLon() == null || cityDetails.getTemperature() == null) {
			model.addAttribute("error", cityDetails);
			return "pages/cityform";
		}

		cityService.saveOrUpdate(city);

		return "redirect:/cities/";
		// city/" + city.getId();
	}

	@RequestMapping("/cities/edit/{id}")
	public String cityForm(@PathVariable Integer id, Model model) {

		CityToCityForm cityToCityForm = new CityToCityForm();
		City city = cityService.getById(id);

		model.addAttribute("cityForm", cityToCityForm.convert(city));

		return "pages/cityform";
	}
}
