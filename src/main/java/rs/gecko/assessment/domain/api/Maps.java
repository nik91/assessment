package rs.gecko.assessment.domain.api;

import javax.persistence.Entity;

@Entity
public class Maps extends BaseApi {

	@Override
	public String toString() {
		return getUrl() + "{city}" + getParametars();
	}

	
}
