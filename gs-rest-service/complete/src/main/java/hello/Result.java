package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
	
	private String resolvedQuery;
	private String action;
	private Map<String,String> parameters=new HashMap<String, String>();
	private List<String> contexts=new ArrayList<String>();
	public String getResolvedQuery() {
		return resolvedQuery;
	}
	public void setResolvedQuery(String resolvedQuery) {
		this.resolvedQuery = resolvedQuery;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	public List<String> getContexts() {
		return contexts;
	}
	public void setContexts(List<String> contexts) {
		this.contexts = contexts;
	}

}
