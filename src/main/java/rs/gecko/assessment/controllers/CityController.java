package rs.gecko.assessment.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
import rs.gecko.assessment.services.reposervices.WeatherServiceRepoImpl;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */

@Secured("ROLE_ADMIN")
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

	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherServiceRepoImpl.class);

	/**
	 * Open Cities page and show list of cities
	 * 
	 * @param model
	 *            send list of cities and information that configurations are set or
	 *            not.
	 * @return pages/cities.html
	 */
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

	/**
	 * Open empty CityForm where user can populate form with new data
	 * 
	 * @param model
	 *            new CityForm and viewOption
	 * @return empty CityForm
	 */
	@RequestMapping("/cities/new")
	public String newCity(Model model) {
		model.addAttribute("cityForm", new CityForm());
		model.addAttribute("viewOption", "cityForm.new");
		model.addAttribute("CityActive", "active");
		return "pages/cityform";
	}

	/**
	 * Resolve Post request from CityForm
	 * 
	 * @param cityForm
	 *            get cityForm
	 * @param bindingResult
	 *            get errors from validations
	 * @param model
	 *            send errors or additional information to show to user
	 * @return cityForme if there is any error or redirect to Cities page
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.POST)
	public String saveOrUpdateCity(@Valid CityForm cityForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("viewOption", "cityForm.new");
			model.addAttribute("CityActive", "active");
			return "pages/cityform";
		}

		City city = cityFormToCity.convert(cityForm);
		CityDetails cityDetails = cityService.getCityDetails(city);

		if (cityDetails.getLat() == null || cityDetails.getLon() == null || cityDetails.getTemperature() == null) {
			model.addAttribute("error", cityDetails);
			model.addAttribute("viewOption", "cityForm.new");
			model.addAttribute("CityActive", "active");
			return "pages/cityform";
		}

		cityService.saveOrUpdate(city);
		if (city != null) { // Added to satisfy test needs. Logger was throwing error.
		LOGGER.info("Saved or Updated city: " + city.toString());
		}

		return "redirect:/cities/";
		// city/" + city.getId();
	}

	/**
	 * Get city from database and populate cityForm
	 * 
	 * @param id
	 *            is parameter to identify city in database
	 * @param model
	 *            send, converted city to cityForm
	 * @return page with form that is populated with data of object for edit
	 */
	@RequestMapping("/cities/edit/{id}")
	public String cityForm(@PathVariable Integer id, Model model) {

		CityToCityForm cityToCityForm = new CityToCityForm();
		City city = cityService.getById(id);

		model.addAttribute("cityForm", cityToCityForm.convert(city));
		model.addAttribute("viewOption", "cityForm.edit");
		model.addAttribute("CityActive", "active");

		return "pages/cityform";
	}

	/**
	 * Delete city from database by id
	 * 
	 * @param id
	 *            is parameter to identify city in database
	 * @return page cities
	 */
	@RequestMapping("/cities/delete/{id}")
	public String deleteCity(@PathVariable Integer id) {

		cityService.delete(id);

		return "redirect:/cities";
	}

	/**
	 * Check is all APIs configured and have enabled
	 * 
	 * @return true if there is an active configuration for Weather and Map and
	 *         False if some of configuration is absent
	 */
	public boolean isConfigOk() {

		if (mapService.findEnabledTrue() == null || weatherService.findEnabledTrue() == null) {
			return false;
		}

		return true;
	}
}
