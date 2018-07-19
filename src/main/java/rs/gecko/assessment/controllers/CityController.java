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
import rs.gecko.assessment.converters.city.CityFormToCity;
import rs.gecko.assessment.converters.city.CityToCityForm;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;

@Controller
public class CityController {

	@Autowired
	private CityService cityService;

	@Autowired
	private CityFormToCity cityFormToCity;

	@Autowired
	private MapService mapService;

	@Autowired
	private WeatherService weatherService;

	@RequestMapping("/cities")
	public String cities(Model model) {

		List<CityDetails> citiesDetails = new ArrayList<CityDetails>();

		List<City> cities = (List<City>) cityService.listAll();

		cities.forEach(citie -> {
			CityDetails cityDetail = cityService.getCityDetails(citie);

			citiesDetails.add(cityDetail);

		});

		model.addAttribute("cityDetails", citiesDetails);
		model.addAttribute("CityActive", "active");
		model.addAttribute("configOk", isConfigOk());

		return "pages/cities";
	}

	@RequestMapping("/cities/new")
	public String newCity(Model model) {
		model.addAttribute("cityForm", new CityForm());
		model.addAttribute("CityActive", "active");
		model.addAttribute("viewOption", "cityForm.new");
		return "pages/cityform";
	}

	@RequestMapping(value = "/cities", method = RequestMethod.POST)
	public String saveOrUpdateCity(@Valid CityForm cityForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("viewOption", "cityForm.new");
			return "pages/cityform";
		}

		City city = cityFormToCity.convert(cityForm);
		CityDetails cityDetails = cityService.getCityDetails(city);

		if (cityDetails.getLat() == null || cityDetails.getLon() == null || cityDetails.getTemperature() == null) {
			model.addAttribute("error", cityDetails);
			model.addAttribute("viewOption", "cityForm.new");
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
		model.addAttribute("CityActive", "active");
		model.addAttribute("viewOption", "cityForm.edit");

		return "pages/cityform";
	}

	@RequestMapping("/cities/delete/{id}")
	public String deleteCity(@PathVariable Integer id) {

		cityService.delete(id);

		return "redirect:/cities";
	}

	public boolean isConfigOk() {

		if (mapService.findEnabled(true) == null || weatherService.findEnabled(true) == null) {
			return false;
		}

		return true;
	}
}
