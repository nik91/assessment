package rs.gecko.assessment.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.converters.weather.ApiFormToWeatherConfig;
import rs.gecko.assessment.converters.weather.WeatherConfigToApiForm;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.services.WeatherService;
import rs.gecko.assessment.services.reposervices.WeatherServiceRepoImpl;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
@RequestMapping("/configs/Weather")
@Controller
@Secured("ROLE_ADMIN")
public class ConfigWeatherController {

	@Autowired
	WeatherService weatherService;

	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherServiceRepoImpl.class);

	private ApiFormToWeatherConfig apiFormToWeatherConfig = new ApiFormToWeatherConfig();
	private WeatherConfigToApiForm weatherConfigToApiForm = new WeatherConfigToApiForm();

	/**
	 * Delete weather configuration from database by id
	 * 
	 * @param id
	 *            is parameter to identify weather configuration in database
	 * @return page configs
	 */
	@RequestMapping("/delete/{id}")
	public String configPage(@PathVariable Integer id, Model model) {

		weatherService.delete(id);
		return "redirect:/configs";
	}

	/**
	 * Open empty apiForm where user can populate form with new data
	 * 
	 * @param model
	 *            new apiForm and viewOption, formType, formAction, formTitle
	 * @return empty apiForm
	 */
	@RequestMapping("/new")
	public String newWeatherConfig(Model model) {
		model.addAttribute("apiForm", new ApiForm());
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "apiForm.viewOption.new");
		model.addAttribute("formType", "Weather");
		model.addAttribute("formAction", "/configs/Weather/update");
		model.addAttribute("formTitle", "apiForm.weather");
		return "pages/apiform";
	}

	/**
	 * Resolve Post request from apiForm
	 * 
	 * @param apiForm
	 *            get apiForm
	 * @param bindingResult
	 *            get errors from validations
	 * @param model
	 *            send errors or additional information to show to user
	 * @return cityForme if there is any error, or redirect to Configs page
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String WeatherForm(@Valid ApiForm apiForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			boolean validationOk = false;
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {

				if (error.getField().equals("url") || error.getField().equals("apiKey")) {
					populateModelForEditForm(model);

					return "pages/apiform";
				} else {
					validationOk = true;
				}
			}

			populateModelForEditForm(model);

			if (!validationOk) {
				return "pages/apiform";
			}
		}

		Weather weather = apiFormToWeatherConfig.convert(apiForm);

		weatherService.saveOrUpdate(weather);
		LOGGER.info("Saved or Updated city: " + weather.toString());
		return "redirect:/configs";
	}

	/**
	 * Get weather configuration from database and populate apiForm
	 * 
	 * @param id
	 *            is parameter to identify weather configuration in database
	 * @param model
	 *            send, converted weather configuration to apiForm
	 * @return page with form that is populated with data of object for edit
	 */
	@RequestMapping("/edit/{id}")
	public String WeatherConfigEdit(@PathVariable Integer id, Model model) {

		Weather weather = weatherService.getById(id);

		model.addAttribute("apiForm", weatherConfigToApiForm.convert(weather));
		populateModelForEditForm(model);

		return "pages/apiform";
	}

	/**
	 * Populate model with group of object
	 * 
	 * @param model
	 *            send active component of navigation, form title, form action, form
	 *            type, viewOption
	 */
	private void populateModelForEditForm(Model model) {
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "apiForm.viewOption.edit");
		model.addAttribute("formType", "Weather");
		model.addAttribute("formAction", "/configs/Weather/update");
		model.addAttribute("formTitle", "apiForm.weather");

	}

}