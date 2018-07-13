package rs.gecko.assessment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rs.gecko.assessment.externalservices.weather.WeatherParam;
import rs.gecko.assessment.services.WeatherService;

@Controller
public class WeatherContorller {

	@Autowired
	private WeatherService watherService;

	@RequestMapping(value = "/api/wather", method = RequestMethod.GET)
	public ResponseEntity<WeatherParam> getWeather(/* @PathVariable String city, @PathVariable String state */) {
		WeatherParam weatherParam = watherService.getData("Belgrade");
		System.out.println(weatherParam.getId());
		return new ResponseEntity<WeatherParam>(weatherParam, HttpStatus.OK);

	}

	@RequestMapping(value = "/wather/{city}")
	public String watherForCity(@PathVariable String city, Model model) {
		model.addAttribute("wather", watherService.getData(city));

		return "/wather/wather";
	}

}
