<%@ include file="../includes/before_head.jsp"%>
<title>sign-up.title</title>
<%@ include file="../includes/after_head.jsp"%>

<%@ include file="../includes/before_nav.jsp"%>
<%@ include file="../includes/after_nav.jsp"%>

<div class="container" id="login-div">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form id="login-form" action="signup" class=" centered">
				<div class="form-group">
					<input type="text" name="name" placeholder="Username" required>
				</div>
				<div class="form-group">
					<input type="email" name="email" placeholder="Email" required>
				</div>
				<div class="form-group">
					<input type="password" name="password" placeholder="Password" required>
				</div>
				<!-- <input type="checkbox" name="remember.me" id="remember.me"> -->
				<!-- <label for="remember.me">Remember me</label> -->
				<input type="submit" class="btn btn-default" value="Sign up">
			</div>
		</form>
	</div>
</div>

<%@ include file="../includes/footer.jsp"%>
