<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/user.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/comments.css"	type="text/css">
	<title>Comments</title>
<%@ include file="../../includes/after_head.jsp"%>


<%@ include file="../../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/get/customer/leaveProposal" class="custom-underline">Leave proposal</a></li>
			<li><a href="${pageContext.request.contextPath}/get/customer/proposals" class="custom-underline">My proposals</a></li>
			<li><a href="#" class="custom-underline active">Comments</a></li>
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
				    	<input type="submit" class="btn btn-default button-cool" name="page" value="previous">
				    </c:if>
				    <c:if test="${currentPage < lastPage}">
				    	<input type="submit" class="btn btn-default button-cool" name="page" value="next">
					</c:if>
				</form>
			</div>
		</div>
	</div>
</div>



<%@ include file="../../includes/footer.jsp"%>
