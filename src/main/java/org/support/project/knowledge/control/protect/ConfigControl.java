package org.support.project.knowledge.control.protect;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance=Instance.Prototype)
public class ConfigControl extends Control {
	
	@Get
	public Boundary index() {
		return forward("index.jsp");
	}
}
