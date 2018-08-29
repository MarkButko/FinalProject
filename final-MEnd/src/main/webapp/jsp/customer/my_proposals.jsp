<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/user.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/proposal.css"	type="text/css">
	<title>My proposals</title>
<%@ include file="../../includes/after_head.jsp"%>

<%@ include file="../../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/get/customer/leaveProposal" class="custom-underline ">Leave proposal</a></li>
			<li><a href="#" class="custom-underline active">My proposals</a></li>
			<li><a href="${pageContext.request.contextPath}/get/customer/comments" class="custom-underline">Comments</a></li>
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
					<p>Manager: ${proposal.manager.name}</p>
					<hr />
				</c:if>

				<c:if test="${not empty master}">
					<p>Master: ${proposal.master.name}</p>
					<hr />
				</c:if>

				<h4>Proposal text:</h4>
				<p>${proposal.message}</p>
				<hr />

				<c:if test="${not empty proposal.rejectionCause}">
					<h4>Rejection cause:</h4>
					<p>${proposal.rejectionCause}</p>
				</c:if>

				<c:if test="${not empty proposal.price and proposal.price != 0}">
					<h4>Price: ${proposal.price}</h4>
				</c:if>

				<c:if test="${not empty proposal.comment}">
					<h4>Your comment:</h4>
					<p>${proposal.comment}</p>
				</c:if>
				<c:if test="${empty proposal.comment and proposal.status == 'COMPLETED'}">
					<div class="comment-div">
						<form action="${pageContext.request.contextPath}/get/comment" method="POST">
							<div class="form-group">
								<textarea name="comment" placeholder="Comment here" required></textarea>
							</div>
							<input type="hidden" name="id" value="${proposal.id}"/>
							<input type="submit" class="btn btn-default button-cool" value="Comment">
						</form>
					</div>
				</c:if>

			</div>
		</c:forEach>

		<div class="row">
			<div class="col-md-offset-5 col-md-2 pagination-div">
				<form action="${pageContext.request.contextPath}/get/customer/proposalsPagination" method="post">
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
