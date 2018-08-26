package mark.butko.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import mark.butko.model.entity.Proposal;

public class ProposalsTag extends BodyTagSupport {

	private ArrayList<Proposal> proposals;
	private Iterator iterator;
	private Proposal proposal;

	@Override
	public int doStartTag() throws JspException {
		proposals = (ArrayList<Proposal>) pageContext.findAttribute("proposals");
		if (proposals == null || proposals.isEmpty()) {
			return SKIP_BODY;
		} else {
			return EVAL_BODY_BUFFERED;
		}
	}

	@Override
	public void doInitBody() throws JspException {
		iterator = proposals.iterator();
		if (iterator.hasNext()) {
			proposal = (Proposal) iterator.next();
			this.setAttributes(proposal);
		}
	}

	private void setAttributes(Proposal proposal) {
		pageContext.setAttribute("author", proposal.getAuthor());
		pageContext.setAttribute("id", proposal.getId());
		pageContext.setAttribute("master", proposal.getMaster());
		pageContext.setAttribute("manager", proposal.getManager());
		pageContext.setAttribute("date", proposal.getDate());
		pageContext.setAttribute("message", proposal.getMessage());
		pageContext.setAttribute("rejection_cause", proposal.getRejectionCause());
		pageContext.setAttribute("status", proposal.getStatus());
		pageContext.setAttribute("comment", proposal.getComment());
		pageContext.setAttribute("price", proposal.getPrice());
	}

	@Override
	public int doAfterBody() throws JspException {
		try {
			if (iterator.hasNext()) {
				proposal = (Proposal) iterator.next();
				this.setAttributes(proposal);
				return EVAL_BODY_AGAIN;
			} else {
				JspWriter out = bodyContent.getEnclosingWriter();
				bodyContent.writeOut(out);
				return SKIP_BODY;
			}
		} catch (IOException ioe) {
			return SKIP_BODY;
		}
	}
}
