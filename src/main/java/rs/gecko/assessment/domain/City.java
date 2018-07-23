package rs.gecko.assessment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 * 
 *         Contains information about City Name and City State
 */
@Entity
public class City implements DomainObject{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * City Name
	 */
	private String name;

	/**
	 * City State
	 */
	private String state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return String City name with City State in format (CityName,State)
	 */
	public String getCityAndState() {
		if (state != null && !state.isEmpty()) {
			return name + "," + state;
		}

		return name;
	}
	
}
