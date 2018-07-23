package rs.gecko.assessment.externalservices.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Nikola Karovic
 *
 *         gecko SOLUTIONS
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeather {

	private Double temp;
	private Double pressure;
	private Double humidity; 
	private Double tempMin;
	private Double tempMax;
	
	public MainWeather() {
		
	}
	
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	public Double getPressure() {
		return pressure;
	}
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public Double getTempMin() {
		return tempMin;
	}
	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}
	public Double getTempMax() {
		return tempMax;
	}
	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}
	
	
}
