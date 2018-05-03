package org.support.project.web.boundary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractBoundary implements Boundary {
	/** HttpServletRequest */
	private HttpServletRequest request;
	/** HttpServletResponse */
	private HttpServletResponse response;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}


}
