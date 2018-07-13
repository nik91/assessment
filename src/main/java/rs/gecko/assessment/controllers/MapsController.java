package rs.gecko.assessment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.services.MapService;

@RestController
public class MapsController {

	@Autowired
	private MapService mapController;

	@RequestMapping(value = "/api/maps", method = RequestMethod.GET)
	public ResponseEntity<Location> getLocation(/* @PathVariable String city, @PathVariable String state */) {
		Location location = mapController.getData("Novi Sad");
		System.out.println(location.getLat() + " " + location.getLon());
		return new ResponseEntity<Location>(location, HttpStatus.OK);

	}

	/*@RequestMapping(value = "/wather/{city}")
	public String watherForCity(@PathVariable String city, Model model) {
		model.addAttribute("wather", watherService.getData(city));

		return "/wather/wather";
	}*/

}
