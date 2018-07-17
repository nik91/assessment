package rs.gecko.assessment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.gecko.assessment.services.MapService;

@Controller
@RequestMapping("/configs/Map")
public class ConfigMapController {

	@Autowired
	MapService mapService;

	@RequestMapping("/delete/{id}")
	public String configPage(@PathVariable Integer id, Model model) {


		System.out.println(mapService.getById(id));
		mapService.delete(id);
		return "redirect:/configs";
	}

}
