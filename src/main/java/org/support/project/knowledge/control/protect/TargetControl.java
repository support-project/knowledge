package org.support.project.knowledge.control.protect;

import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.vo.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

public class TargetControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(GroupControl.class);
	
	public static final int PAGE_LIMIT = 10;
	
	/**
	 * グループ選択のための候補をJSONで取得
	 * @return
	 * @throws InvalidParamException 
	 */
	@Get
	public Boundary typeahead() throws InvalidParamException {
		LOG.trace("call typeahead");
		String keyword = super.getParam("keyword");
		int offset = 0;
		int limit = 10;
		String off = super.getParam("offset");
		if (StringUtils.isInteger(off)) {
			offset = Integer.parseInt(off);
		}
		
		TargetLogic groupLogic = TargetLogic.get();
		List<LabelValue> aHeads = groupLogic.selectOnKeyword(keyword, super.getLoginedUser(), offset * limit, limit);
		return send(aHeads);
	}
	
}
