package rs.gecko.assessment.domain.api;

import javax.persistence.Entity;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Define Map API configuration
 */
@Entity
public class Maps extends BaseApi {

	@Override
	public String toString() {
		return getUrl() + "{city}" + getParametars();
	}

	
}
