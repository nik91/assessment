package rs.gecko.assessment.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rs.gecko.assessment.commands.ApiForm;
import rs.gecko.assessment.converters.maps.ApiFormToMapConfig;
import rs.gecko.assessment.converters.maps.MapConfigToApiForm;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.reposervices.WeatherServiceRepoImpl;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 */
@RequestMapping("/configs/Map")
@Controller
public class ConfigMapController {

	@Autowired
	MapService mapService;

	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherServiceRepoImpl.class);

	private MapConfigToApiForm mapConfigToApiForm = new MapConfigToApiForm();
	private ApiFormToMapConfig apiFormToMapConfig = new ApiFormToMapConfig();

	/**
	 * Delete maps configuration from database by id
	 * 
	 * @param id
	 *            is parameter to identify maps configuration in database
	 * @return page configs
	 */
	@RequestMapping("/delete/{id}")
	public String configPage(@PathVariable Integer id, Model model) {

		System.out.println(mapService.getById(id));
		mapService.delete(id);
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
	public String newMapConfig(Model model) {
		model.addAttribute("apiForm", new ApiForm());
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "apiForm.viewOption.new");
		model.addAttribute("formType", "Map");
		model.addAttribute("formAction", "/configs/Map/update");
		model.addAttribute("formTitle", "apiForm.maps");
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
	public String MapForm(@Valid ApiForm apiForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			boolean validationOk = false;
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {

				if (error.getField().equals("parametars") || error.getField().equals("url")) {
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

		Maps map = apiFormToMapConfig.convert(apiForm);

		mapService.saveOrUpdate(map);
		LOGGER.info("Saved or Updated city: " + map.toString());

		return "redirect:/configs";

	}

	/**
	 * Get maps configuration from database and populate apiForm
	 * 
	 * @param id
	 *            is parameter to identify maps configuration in database
	 * @param model
	 *            send, converted maps configuration to apiForm
	 * @return page with form that is populated with data of object for edit
	 */
	@RequestMapping("/edit/{id}")
	public String MapConfigEdit(@PathVariable Integer id, Model model) {

		Maps map = mapService.getById(id);

		ApiForm apiForm = mapConfigToApiForm.convert(map);
		model.addAttribute("apiForm", apiForm);
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
		model.addAttribute("formType", "Map");
		model.addAttribute("formAction", "/configs/Map/update");
		model.addAttribute("formTitle", "apiForm.maps");

	}

}