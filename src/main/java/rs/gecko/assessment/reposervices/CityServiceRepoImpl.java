package rs.gecko.assessment.reposervices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.gecko.assessment.domain.City;
import rs.gecko.assessment.services.CityService;
import rs.gecko.repositories.CityRepository;

@Service
public class CityServiceRepoImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public List<?> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City saveOrUpdate(City domainObject) {
		// TODO Auto-generated method stub
		return cityRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
