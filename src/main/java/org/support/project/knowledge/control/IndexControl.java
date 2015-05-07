package org.support.project.knowledge.control;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance=Instance.Prototype)
public class IndexControl extends Control {
	/** ログ */
	private static Log log = LogFactory.getLog(IndexControl.class);
	
}
