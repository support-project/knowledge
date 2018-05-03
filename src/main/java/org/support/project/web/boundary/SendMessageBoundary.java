package org.support.project.web.boundary;

public class SendMessageBoundary extends AbstractBoundary {
	
	private int status;
	
	private String msg;
	
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	@Override
	public void navigate() throws Exception {
		getResponse().sendError(status, msg);
	}

}
