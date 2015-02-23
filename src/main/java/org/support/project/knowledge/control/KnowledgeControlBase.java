package org.support.project.knowledge.control;

import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance=Instance.Prototype)
public class KnowledgeControlBase extends Control {
	
	protected String setViewParam() {
		StringBuilder params = new StringBuilder();
		params.append("?keyword=").append(getParamWithDefault("keyword", ""));
		params.append("&tag=").append(getParamWithDefault("tag", ""));
		params.append("&user=").append(getParamWithDefault("user", ""));
		params.append("&offset=").append(getParamWithDefault("offset", ""));
		setAttribute("params", params.toString());
		return params.toString();
	}

	
	
	
}
