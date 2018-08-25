<%@ include file="../includes/before_head.jsp"%>
<link rel="stylesheet"	href="../../css/user.css"	type="text/css">
<title>leave proposal</title>
<%@ include file="../includes/after_head.jsp"%>


<%@ include file="../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="#" class="custom-underline active">Leave proposal</a></li>
			<li><a href="#" class="custom-underline">My proposals</a></li>
			<li><a href="#" class="custom-underline">Comments</a></li>
			<hr />
		</ul>
	</div>
	<%@ include file="../includes/logout.jsp"%>
<%@ include file="../includes/after_nav.jsp"%>


<div class="container" id="login-div">
	<div class="row">
		<div class="col-md-offset-3 col-md-6">
			<form id="proposal-form" action="proposal" class="centered">
				<div class="form-group">
					<textarea name="message" placeholder="Your proposal" required></textarea>
				</div>
				<input type="submit" class="btn btn-default" value="Submit">
			</form>
		</div>
	</div>
</div>


<%@ include file="../includes/footer.jsp"%>
