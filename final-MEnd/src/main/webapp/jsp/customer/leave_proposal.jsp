<%@ include file="../../includes/before_head.jsp"%>
<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/user.css"	type="text/css">
<title><fmt:message key="title.customer.leave.proposal" /></title>
<%@ include file="../../includes/after_head.jsp"%>


<%@ include file="../../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="#" class="custom-underline active"><fmt:message key="menu.leave.proposal" /></a></li>
			<li><a href="${pageContext.request.contextPath}/get/customer/proposals" class="custom-underline"><fmt:message key="menu.my.proposals" /></a></li>
			<li><a href="${pageContext.request.contextPath}/get/customer/comments" class="custom-underline"><fmt:message key="menu.comments" /></a></li>
			<hr />
		</ul>
	</div>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>


<div class="container" id="login-div">
	<div class="row">
		<div class="col-md-offset-3 col-md-6">
			<form id="proposal-form" action="leaveProposal" class="centered" method="POST">
				<div class="form-group">
					<textarea name="message" placeholder='<fmt:message key="label.your.proposal" />' required></textarea>
				</div>
				<input type="submit" class="btn btn-default" value='<fmt:message key="button.submit" />'>
			</form>
		</div>
	</div>
</div>


<%@ include file="../../includes/footer.jsp"%>
