package rs.gecko.assessment.domain;

import java.util.Date;

public class LogActiveConfigs {

	private Integer configId;
	private Date lastUsed;

	private String url;
	private String parametars;
	private String apiKey;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
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

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public LogActiveConfigs(Integer configId, String url, String parametars, String apiKey, Date lastUsed) {
		super();
		this.configId = configId;
		this.url = url;
		this.parametars = parametars;
		this.apiKey = apiKey;
		this.lastUsed = lastUsed;
	}

	public LogActiveConfigs() {
	}
}
