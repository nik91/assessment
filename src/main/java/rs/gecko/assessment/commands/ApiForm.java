package rs.gecko.assessment.commands;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;


/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Command class for API Form (Maps and Weather form)
 */
public class ApiForm {

	private Integer id;
	@NotEmpty
	@URL
	private String url;
	@NotEmpty
	@Size(min = 4, max = 20)
	private String parametars;
	private boolean enabled;
	@NotEmpty
	private String apiKey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParametars() {
		return parametars;
	}

	public void setParametars(String parametars) {
		this.parametars = parametars;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
