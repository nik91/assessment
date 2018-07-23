package rs.gecko.assessment.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import rs.gecko.assessment.AssessmentApplication;
import rs.gecko.assessment.domain.City;

@RunWith(SpringRunner.class)
@Import(AssessmentApplication.class)
public class CityServiceRepoImplTest {

	@Autowired
	private CityService cityService;

	@Test
	@Transactional
	public void testListCities() {
		List<City> cities = (List<City>) cityService.listAll();

		cities.forEach(city -> {
			System.out.println(city.getName());
		});

		assert cities.size() == 4;

	}

	@Test
	@Transactional
	public void testDeleteCity() {

		List<City> cities = (List<City>) cityService.listAll();

		cityService.delete(cities.get(0).getId());

		List<City> citiesAfterDelete = (List<City>) cityService.listAll();

		assert citiesAfterDelete.size() == 3;

	}

	@Test
	@Transactional
	public void testSaveCity() {

		City city = new City();
		city.setName("Belgrade");
		City saveCity = cityService.saveOrUpdate(city);

		assert saveCity.getId() != null;
	}

	@Test
	@Transactional
	public void testFindCityById() {

		City city = new City();
		city.setName("Belgrade");
		City saveCity = cityService.saveOrUpdate(city);

		City savedCity = cityService.getById(saveCity.getId());

		assert saveCity == savedCity;
	}

}
