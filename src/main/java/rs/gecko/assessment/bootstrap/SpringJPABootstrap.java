package rs.gecko.assessment.bootstrap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.domain.api.Weather;
import rs.gecko.assessment.domain.security.Role;
import rs.gecko.assessment.domain.security.User;
import rs.gecko.assessment.services.CityService;
import rs.gecko.assessment.services.MapService;
import rs.gecko.assessment.services.RoleService;
import rs.gecko.assessment.services.UserService;
import rs.gecko.assessment.services.WeatherService;

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private CityService cityService;

	@Autowired
	private MapService mapService;

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadCities();
		loadConfig();
		loadUsers();
		loadRoles();
		setAdminRole();
	}

	private void setAdminRole() {
		List<Role> roles = (List<Role>) roleService.listAll();
		List<User> users = (List<User>) userService.listAll();

		roles.forEach(role -> {
			if (role.getRole().equalsIgnoreCase("ADMIN")) {
				users.forEach(user -> {
					user.addRole(role);
					userService.saveOrUpdate(user);
				});
			}
		});

	}

	private void loadRoles() {

		Role adminRole = new Role();
		adminRole.setRole("ADMIN");
		roleService.saveOrUpdate(adminRole);
	}

	private void loadUsers() {
		User user = new User();
		user.setUserName("karec91@gmail.com");
		user.setPassword("nikola91");
		userService.saveOrUpdate(user);

		User user1 = new User();
		user1.setUserName("nikolak@gecko.rs");
		user1.setPassword("nikola91");
		userService.saveOrUpdate(user1);
	}

	private void loadConfig() {
		Maps map = new Maps();
		map.setEnabled(true);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");
		mapService.saveOrUpdate(map);

		Maps map1 = new Maps();
		map1.setEnabled(false);
		map1.setParametars("?format=json");
		map1.setUrl("https://nominatim.openstreetmap.org/search/");
		mapService.saveOrUpdate(map1);

		Weather weather = new Weather();
		weather.setEnabled(true);
		weather.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather.setParametars("Belgrade");
		weather.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");
		weatherService.saveOrUpdate(weather);

		Weather weather1 = new Weather();
		weather1.setEnabled(false);
		weather1.setApiKey("5cf206b12a8b4ca9e171d70bdb6be856");
		weather1.setParametars("Belgrade");
		weather1.setUrl("http://api.openweathermap.org/data/2.5/weather?q=");
		weatherService.saveOrUpdate(weather1);

	}

	private void loadCities() {

		City city = new City();
		city.setName("Kragujevac");
		cityService.saveOrUpdate(city);

		City city1 = new City();
		city1.setName("Novi Sad");
		cityService.saveOrUpdate(city1);

		City city2 = new City();
		city2.setName("Gornji Milanovac");
		cityService.saveOrUpdate(city2);

	}

}
