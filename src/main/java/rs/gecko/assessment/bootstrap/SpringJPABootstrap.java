package rs.gecko.assessment.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.services.CityService;
@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{


	private CityService cityService;
	
	@Autowired
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadCities();
		
	}

	private void loadCities() {

		City city = new City();
		city.setName("Belgrade");
		cityService.saveOrUpdate(city);
		
		
		City city1 = new City();
		city1.setName("GM");
		cityService.saveOrUpdate(city1);
		
		
	}

}
