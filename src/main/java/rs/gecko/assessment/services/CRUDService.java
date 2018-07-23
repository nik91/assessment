package rs.gecko.assessment.services;

import java.util.List;


/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 * @param <T>
 */
public interface CRUDService<T> {

	List<?> listAll();

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
	
}
