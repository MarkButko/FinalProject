<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/user.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/comments.css"	type="text/css">
	<title><fmt:message key="title.customer.comments" /></title>
<%@ include file="../../includes/after_head.jsp"%>


<%@ include file="../../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/get/customer/leaveProposal" class="custom-underline"><fmt:message key="menu.leave.proposal" /></a></li>
			<li><a href="${pageContext.request.contextPath}/get/customer/proposals" class="custom-underline"><fmt:message key="menu.my.proposals" /></a></li>
			<li><a href="#" class="custom-underline active"><fmt:message key="menu.comments" /></a></li>
			<hr />
		</ul>
	</div>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>


<div class="container">
	<div class="row">
		<c:forEach items="${comments}" var="comment">
			<c:if test="${not empty comment.comment}">
				<div class="col-md-offset-2 col-md-6 comment-div">
					<label>${comment.authorName}</label>
					<hr />
					<p>${comment.comment}</p>
				</div>
			</c:if>
		</c:forEach>
	</div>

	<div class="row">
			<div class="col-md-offset-5 col-md-2 pagination-div">
				<form action="${pageContext.request.contextPath}/get/customer/comments" method="post">
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
