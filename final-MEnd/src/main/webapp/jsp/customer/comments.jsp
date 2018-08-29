<%@ include file="../includes/before_head.jsp"%>
	<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
	<link rel="stylesheet"	href="css/user.css"	type="text/css">
	<link rel="stylesheet"	href="css/comments.css"	type="text/css">
	<title>Comments</title>
<%@ include file="../includes/after_head.jsp"%>


<%@ include file="../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="#" class="custom-underline">Leave proposal</a></li>
			<li><a href="#" class="custom-underline">My proposals</a></li>
			<li><a href="#" class="custom-underline active">Comments</a></li>
			<hr />
		</ul>
	</div>
<%@ include file="../includes/after_nav.jsp"%>


<div class="container">
	<div class="row">
		<cstm:proposals>
			<div class="col-md-offset-2 col-md-6 comment-div">
				<label>${author.name}</label>
				<hr />
				<p>${comment}</p>
			</div>
		</cstm:proposals>
	</div>
</div>


<%@ include file="../includes/footer.jsp"%>
