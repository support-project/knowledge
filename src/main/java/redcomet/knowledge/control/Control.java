package redcomet.knowledge.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import redcomet.common.bean.ValidateError;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.log.LogLevel;
import redcomet.common.util.HtmlUtils;
import redcomet.web.util.JspUtil;

public abstract class Control extends redcomet.web.control.Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(Control.class);

	public static final String PATH_ANTISAMY_POLICY = JspUtil.PATH_ANTISAMY_POLICY;

	public static final String MSG_INFO = "NOTIFY_MSG_INFO";
	public static final String MSG_SUCCESS = "NOTIFY_MSG_SUCCESS";
	public static final String MSG_WARN = "NOTIFY_MSG_WARN";
	public static final String MSG_ERROR = "NOTIFY_MSG_ERROR";

	private List<String> infos = null;
	private List<String> successes = null;
	private List<String> warns = null;
	private List<String> errors = null;

	@Override
	public void setRequest(HttpServletRequest request) {
		super.setRequest(request);
		infos = new ArrayList<String>();
		successes = new ArrayList<String>();
		warns = new ArrayList<String>();
		errors = new ArrayList<String>();

		request.setAttribute(MSG_INFO, infos);
		request.setAttribute(MSG_SUCCESS, successes);
		request.setAttribute(MSG_WARN, warns);
		request.setAttribute(MSG_ERROR, errors);
	}

	protected void addMsgInfo(String msg) {
		infos.add(HtmlUtils.escapeHTML(msg));
	}

	protected void addMsgSuccess(String msg) {
		successes.add(HtmlUtils.escapeHTML(msg));
	}

	protected void addMsgWarn(String msg) {
		warns.add(HtmlUtils.escapeHTML(msg));
	}

	protected void addMsgError(String msg) {
		errors.add(HtmlUtils.escapeHTML(msg));
	}

	protected void setResult(String successMsg, List<ValidateError> errors) {
		if (errors == null || errors.isEmpty()) {
			addMsgSuccess(successMsg);
		} else {
			for (ValidateError validateError : errors) {
				if (validateError.getLevel().intValue() == LogLevel.ERROR.getValue()) {
					addMsgError(validateError.getMsg());
				} else {
					addMsgWarn(validateError.getMsg());
				}
			}
		}
	}

	public static String doSamy(String str) throws PolicyException, ScanException {
		Policy policy = Policy.getInstance(Control.class.getResourceAsStream(PATH_ANTISAMY_POLICY));
		AntiSamy as = new AntiSamy();
		CleanResults cr = as.scan(str, policy);
		String escape = cr.getCleanHTML();
		if (LOG.isDebugEnabled()) {
			if (str != null && !str.equals(escape)) {
				LOG.debug("escape string\n before:" + str + "\naftter:" + escape);
			}
		}
		return escape;
	}

	/* (non-Javadoc)
	 * @see redcomet.web.control.Control#copy(redcomet.web.control.Control)
	 */
	@Override
	protected void copy(redcomet.web.control.Control control) {
		super.copy(control);
		if (control instanceof Control) {
			Control c = (Control) control;
			for (String string : infos) {
				c.addMsgInfo(string);
			}
			for (String string : successes) {
				c.addMsgSuccess(string);
			}
			for (String string : warns) {
				c.addMsgWarn(string);
			}
			for (String string : errors) {
				c.addMsgError(string);
			}
		}
	}


}
