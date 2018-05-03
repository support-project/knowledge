package org.support.project.web.util;

import org.support.project.common.exception.ParseException;
import org.support.project.web.logic.SanitizingLogic;

public class AntiSamyEscapeSample {
	public static void main(String[] args) throws ParseException {
		
		String str = "#### タイトル\n- ほげほげ<script>alert('hoge');</script>";
		System.out.println(str);
		
//		Policy policy = Policy.getInstance(AntiSamyEscapeSample.class.getResourceAsStream(PATH_ANTISAMY_POLICY));
//		AntiSamy as = new AntiSamy();
//		CleanResults cr = as.scan(str, policy);
//		str = cr.getCleanHTML();

		str = SanitizingLogic.get().sanitize(str);
		System.out.println(str);
	}

}
