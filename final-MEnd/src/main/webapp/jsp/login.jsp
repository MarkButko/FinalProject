<%@ include file="../includes/before_head.jsp"%>
<title><fmt:message key="login.title" /></title>
<%@ include file="../includes/after_head.jsp"%>
<%@ include file="../includes/before_nav.jsp"%>
<%@ include file="../includes/after_nav.jsp"%>

<div class="container" id="login-div">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form id="login-form" action="${pageContext.request.contextPath}/get/login" class="centered" method="POST">
				<div class="form-group">
					<input type="email" name="email" placeholder="Email" required>
				</div>
				<div class="form-group">
					<input type="password" name="password" placeholder="Password" required>
				</div>
				<!-- <input type="checkbox" name="remember.me" id="remember.me"> -->
				<!-- <label for="remember.me">Remember me</label> -->
				<input type="submit" class="btn btn-default" value="Log in">
		</div>
		</form>
	</div>
</div>

<div id="redirect-container" class="container">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<div id="redirect">
				<label>Don't have an account?</label> <a id="sign-up-a" href="${pageContext.request.contextPath}/get/sign-up">Sign up</a>
			</div>
		</div>
	</div>
</div>

<%@ include file="../includes/footer.jsp"%>