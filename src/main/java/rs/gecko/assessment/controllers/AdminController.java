package rs.gecko.assessment.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.CityDetails;
import rs.gecko.assessment.domain.LogActiveConfigs;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.WeatherService;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
@Secured("ROLE_ADMIN")
@Controller
public class AdminController {

	@Autowired
	private CityService cityService;
	@Autowired
	private MapService mapService;
	@Autowired
	private WeatherService weatherService;

	@Autowired
	private LogAcitveConfigsController logActiveConfigsController;

	private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	/**
	 * Show list of cities and view of all configurations and number of cities
	 * 
	 * @param model
	 *            send list of cityDetails, number of configs, number of map
	 *            configs, number of weather configs, is there activated map and
	 *            weather APIs
	 * @return admin page
	 */

	@Secured("ROLE_ADMIN")
	@RequestMapping("/admin")
	public String adminPage(Model model) {

		List<CityDetails> citiesDetails = new ArrayList<CityDetails>();

		List<City> cities = (List<City>) cityService.listAll();

		cities.forEach(citie -> {
			CityDetails cityDetail = cityService.getCityDetails(citie);

			citiesDetails.add(cityDetail);

		});
		Integer noOfConfiguration = noOfConfig();
		LOGGER.info("Number of configurations:" + noOfConfiguration.toString());

		List<LogActiveConfigs> logActiveConfigs = new ArrayList<>();
		logActiveConfigs.addAll(logActiveConfigsController.getLogActiveConfigs());
		Collections.reverse(logActiveConfigs);
		
		model.addAttribute("AdminActive", "active");
		model.addAttribute("logHistory", logActiveConfigs);
		model.addAttribute("cityDetails", citiesDetails);
		model.addAttribute("config", noOfConfiguration);
		model.addAttribute("mapconfig", mapService.listAll().size());
		model.addAttribute("weatherconfig", weatherService.listAll().size());
		model.addAttribute("countofcities", cities.size());
		model.addAttribute("activeMapConfig", mapService.findEnabledTrue() == null);
		model.addAttribute("activeWetherConfig", weatherService.findEnabledTrue() == null);

		return "pages/admin";
	}

	/**
	 * Get list of all configurations APIs and return their sum
	 * 
	 * @return sum of Map and Weather Configurations
	 */
	private Integer noOfConfig() {

		return mapService.listAll().size() + weatherService.listAll().size();

	}

}
