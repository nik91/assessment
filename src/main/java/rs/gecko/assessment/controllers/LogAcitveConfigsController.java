package rs.gecko.assessment.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import rs.gecko.assessment.domain.LogActiveConfigs;

@Component
public class LogAcitveConfigsController {

	private List<LogActiveConfigs> logActiveConfigs = new ArrayList<>();

	public List<LogActiveConfigs> getLogActiveConfigs() {
		return logActiveConfigs;
	}

	public void setLogActiveConfigs(List<LogActiveConfigs> logActiveConfigs) {
		this.logActiveConfigs = logActiveConfigs;
	}

	public void addLog(LogActiveConfigs log) {
		logActiveConfigs.add(log);
	}

}
