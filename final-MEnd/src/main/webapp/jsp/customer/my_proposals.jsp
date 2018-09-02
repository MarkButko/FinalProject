<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/user.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/proposal.css"	type="text/css">
	<title><fmt:message key="title.customer.proposals" /></title>
<%@ include file="../../includes/after_head.jsp"%>

<%@ include file="../../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/get/customer/leaveProposal" class="custom-underline "><fmt:message key="menu.leave.proposal" /></a></li>
			<li><a href="#" class="custom-underline active"><fmt:message key="menu.my.proposals" /></a></li>
			<li><a href="${pageContext.request.contextPath}/get/customer/comments" class="custom-underline"><fmt:message key="menu.comments" /></a></li>
			<hr />
		</ul>
	</div>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>


<div class="container">
	<div class="row">

		<c:forEach items="${proposals}" var="proposal">
			<div class="col-md-offset-2 col-md-6 proposal-div">

				<label class="date">${proposal.date}</label>
				<label>${proposal.status}</label>
				<hr />

				<c:if test="${not empty manager}">
					<p><fmt:message key="label.manager" />: ${proposal.manager.name}</p>
					<hr />
				</c:if>

				<c:if test="${not empty master}">
					<p><fmt:message key="label.master" />: ${proposal.master.name}</p>
					<hr />
				</c:if>

				<h4><fmt:message key="label.proposal.text" />:</h4>
				<p>${proposal.message}</p>
				<hr />

				<c:if test="${not empty proposal.rejectionCause}">
					<h4><fmt:message key="label.rejection.cause" />:</h4>
					<p>${proposal.rejectionCause}</p>
				</c:if>

				<c:if test="${not empty proposal.price and proposal.price != 0}">
					<h4><fmt:message key="label.price" />: ${proposal.price}</h4>
				</c:if>

				<c:if test="${not empty proposal.comment}">
					<h4><fmt:message key="label.comment" />:</h4>
					<p>${proposal.comment}</p>
				</c:if>
				<c:if test="${empty proposal.comment and proposal.status == 'COMPLETED'}">
					<div class="comment-div">
						<form action="${pageContext.request.contextPath}/get/customer/comment" method="POST">
							<div class="form-group">
								<textarea name="comment" placeholder='<fmt:message key="label.comment" />' required></textarea>
							</div>
							<input type="hidden" name="id" value="${proposal.id}"/>
							<input type="submit" class="btn btn-default button-cool" value='<fmt:message key="button.comment" />'>
						</form>
					</div>
				</c:if>

			</div>
		</c:forEach>

		<div class="row">
			<div class="col-md-offset-4 col-md-4 pagination-div">
				<form action="${pageContext.request.contextPath}/get/customer/proposals" method="GET">
					<c:if test="${currentPage > 1}">
				    	<button type="submit" class="btn btn-default button-cool" name="futurePage" value='previous'>
				    		<fmt:message key="button.previous" />
				    </button>
				    </c:if>
				    <c:if test="${currentPage < lastPage}">
					    <button type="submit" class="btn btn-default button-cool" name="futurePage" value='next'>
					    	<fmt:message key="button.next" />
						</button>
					</c:if>
					<input type="hidden" name = "currentPage" value="${requestScope.currentPage}">
				</form>
			</div>
		</div>

	</div>
</div>


<%@ include file="../../includes/footer.jsp"%>
