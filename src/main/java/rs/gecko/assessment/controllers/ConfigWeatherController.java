package rs.gecko.assessment.controllers;

import java.util.List;

import javax.validation.Valid;

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

@RequestMapping("/configs/Weather")
@Controller
@Secured("ROLE_ADMIN")
public class ConfigWeatherController {

	@Autowired
	WeatherService weatherService;

	private ApiFormToWeatherConfig apiFormToWeatherConfig = new ApiFormToWeatherConfig();
	private WeatherConfigToApiForm weatherConfigToApiForm = new WeatherConfigToApiForm();

	@RequestMapping("/delete/{id}")
	public String configPage(@PathVariable Integer id, Model model) {

		weatherService.delete(id);
		return "redirect:/configs";
	}

	@RequestMapping("/new")
	public String newWeatherConfig(Model model) {
		model.addAttribute("apiForm", new ApiForm());
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "New");
		model.addAttribute("formType", "Weather");
		model.addAttribute("formAction", "/configs/Weather/update");
		return "pages/apiform";
	}

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

		return "redirect:/configs";
	}

	@RequestMapping("/edit/{id}")
	public String WeatherConfigEdit(@PathVariable Integer id, Model model) {

		Weather weather = weatherService.getById(id);

		model.addAttribute("apiForm", weatherConfigToApiForm.convert(weather));
		populateModelForEditForm(model);

		return "pages/apiform";
	}

	private void populateModelForEditForm(Model model) {
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "Edit");
		model.addAttribute("formType", "Weather");
		model.addAttribute("formAction", "/configs/Weather/update");

	}

}