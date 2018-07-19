package rs.gecko.assessment.domain.api;

import javax.persistence.Entity;

@Entity
public class Weather extends BaseApi{

	
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	@Override
	public String toString() {
		return getUrl() + "{city}&appid="+apiKey;
	}

}
