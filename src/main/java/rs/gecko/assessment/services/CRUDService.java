package rs.gecko.assessment.services;

import java.util.List;
import java.util.Optional;



public interface CRUDService<T> {

	List<?> listAll();

    Optional<T> getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
	
}
