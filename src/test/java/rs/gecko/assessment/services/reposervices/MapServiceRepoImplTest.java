package rs.gecko.assessment.services.reposervices;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import rs.gecko.assessment.AssessmentApplication;
import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.domain.api.Maps;
import rs.gecko.assessment.externalservices.maps.Location;
import rs.gecko.assessment.services.MapService;

@RunWith(SpringRunner.class)
@Import(AssessmentApplication.class)
public class MapServiceRepoImplTest {

	@Autowired
	private MapService mapService;

	@Test
	@Transactional
	public void testListMapsConfig() {
		List<Maps> weathers = (List<Maps>) mapService.listAll();

		assert weathers.size() == 3;

	}

	@Test
	@Transactional
	public void testDeleteMapConfig() {

		List<Maps> maps = (List<Maps>) mapService.listAll();

		mapService.delete(maps.get(0).getId());

		List<Maps> mapsAfterDelete = (List<Maps>) mapService.listAll();

		assert mapsAfterDelete.size() == 2;

	}

	@Test
	@Transactional
	public void testSaveMapsConfig() {

		Maps map = new Maps();
		map.setEnabled(true);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");

		Maps savedMap = mapService.saveOrUpdate(map);

		assert savedMap.getId() != null;
	}

	@Test
	@Transactional
	public void testFindMapsConfigById() {

		Maps map = new Maps();
		map.setEnabled(true);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");

		Maps saveMap = mapService.saveOrUpdate(map);

		Maps savedMap = mapService.getById(saveMap.getId());

		assert saveMap == savedMap;
	}

	@Test
	@Transactional
	public void setEnabledMapsConfig() {
		Maps map = new Maps();
		map.setEnabled(true);
		map.setParametars("?format=json");
		map.setUrl("https://nominatim.openstreetmap.org/search/");

		Maps savedMap = mapService.saveOrUpdate(map);

		Maps mapEnabled = mapService.findEnabledTrue();

		assert savedMap == mapEnabled;

		savedMap.setEnabled(false);
		mapService.saveOrUpdate(savedMap);

		assert mapService.findEnabledTrue() == null;

	}

	@Test
	@Transactional
	public void getLocationForCity() {

		City city = new City();
		city.setName("Ljig,SN");

		Location location = mapService.getData(city);

		assertNull(location);
		City city1 = new City();
		city1.setName("Belgrade,RS");

		Location location1 = mapService.getData(city1);

		assertNotNull(location1);

	}

}
