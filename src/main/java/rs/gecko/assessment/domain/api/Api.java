package rs.gecko.assessment.domain.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import rs.gecko.assessment.domain.DomainObject;


@Entity
public class Api implements DomainObject{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String url;
	private String parametars;
	private boolean enabled;
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
	
	
}
