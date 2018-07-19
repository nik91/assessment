package rs.gecko.assessment.controllers;

import java.util.List;

import javax.validation.Valid;

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

@RequestMapping("/configs/Map")
@Controller
public class ConfigMapController {

	@Autowired
	MapService mapService;

	private MapConfigToApiForm mapConfigToApiForm = new MapConfigToApiForm();
	private ApiFormToMapConfig apiFormToMapConfig = new ApiFormToMapConfig();

	@RequestMapping("/delete/{id}")
	public String configPage(@PathVariable Integer id, Model model) {

		System.out.println(mapService.getById(id));
		mapService.delete(id);
		return "redirect:/configs";
	}

	@RequestMapping("/new")
	public String newMapConfig(Model model) {
		model.addAttribute("apiForm", new ApiForm());
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "New");
		model.addAttribute("formType", "Map");
		model.addAttribute("formAction", "/configs/Map/update");
		return "pages/apiform";
	}

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

		return "redirect:/configs";

	}

	@RequestMapping("/edit/{id}")
	public String MapConfigEdit(@PathVariable Integer id, Model model) {

		Maps map = mapService.getById(id);

		ApiForm apiForm = mapConfigToApiForm.convert(map);
		model.addAttribute("apiForm", apiForm);
		populateModelForEditForm(model);

		return "pages/apiform";
	}

	private void populateModelForEditForm(Model model) {
		model.addAttribute("ConfigActive", "active");
		model.addAttribute("viewOption", "Edit");
		model.addAttribute("formType", "Map");
		model.addAttribute("formAction", "/configs/Map/update");

	}

}