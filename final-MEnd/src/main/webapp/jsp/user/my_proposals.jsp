<%@ include file="../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="../../css/user.css"	type="text/css">
	<link rel="stylesheet"	href="../../css/proposal.css"	type="text/css">
	<title>My proposals</title>
<%@ include file="../includes/after_head.jsp"%>


<%@ include file="../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="#" class="custom-underline ">Leave proposal</a></li>
			<li><a href="#" class="custom-underline active">My proposals</a></li>
			<li><a href="#" class="custom-underline">Comments</a></li>
			<hr />
		</ul>
	</div>
	<%@ include file="../includes/logout.jsp"%>
<%@ include file="../includes/after_nav.jsp"%>


<div class="container">
	<div class="row">

		<cstm:proposals>
			<div class="col-md-offset-2 col-md-6 proposal-div">

				<label class="date">${date}</label>
				<label>${status.name}</label>
				<hr />

				<c:if test="${not empty manager}">
					<p>Manager: ${manager.name}</p>
					<hr />
				</c:if>

				<c:if test="${not empty master}">
					<p>Master: ${master.name}</p>
					<hr />
				</c:if>

				<h4>Proposal text:</h4>
				<p>${message}</p>
				<hr />

				<c:if test="${not empty rejection_cause}">
					<h4>Rejection cause:</h4>
					<p>${rejection_cause}</p>
				</c:if>

				<c:if test="${not empty price}">
					<h4>Price: ${price}</h4>
				</c:if>

				<c:if test="${not empty comment}">
					<h4>Your comment:</h4>
					<p>${comment}</p>
				</c:if>
				<c:if test="${empty comment}">
					<div class="comment-div">
						<form action="comment" method="POST">
							<div class="form-group">
								<textarea name="comment" placeholder="Comment here" required></textarea>
							</div>
							<input type="submit" class="btn btn-default button-cool" value="Comment">
						</form>
					</div>
				</c:if>

			</div>
		</cstm:proposals>

	</div>
</div>


<%@ include file="../includes/footer.jsp"%>
