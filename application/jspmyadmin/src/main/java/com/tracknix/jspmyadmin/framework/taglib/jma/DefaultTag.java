/**
 * 
 */
package com.tracknix.jspmyadmin.framework.taglib.jma;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.tracknix.jspmyadmin.framework.taglib.support.AbstractTagSupport;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class DefaultTag extends AbstractTagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {

		Tag parentTag = super.getParent();
		SwitchTag switchTag = null;
		if (parentTag instanceof SwitchTag) {
			switchTag = (SwitchTag) parentTag;
		} else {
			throw new JspException("No switch tag found.");
		}

		if (switchTag.isSubDone()) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		SwitchTag switchTag = (SwitchTag) super.getParent();
		switchTag.setSubDone(true);
		return SKIP_BODY;
	}
}
