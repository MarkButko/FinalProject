<%@ include file="../includes/before_head.jsp"%>
<title><fmt:message key="title.signup" /></title>
<%@ include file="../includes/after_head.jsp"%>

<%@ include file="../includes/before_nav.jsp"%>
<%@ include file="../includes/after_nav.jsp"%>

<div class="container" id="login-div">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form id="login-form" action="${pageContext.request.contextPath}/get/sign-up" class=" centered" method="POST">
				<div class="form-group">
					<input type="text" name="name" placeholder='<fmt:message key="label.name" />' value="${name}" required>
				</div>
				<p class="error">${name_error_message}</p>
				<div class="form-group">
					<input type="email" name="email" placeholder='<fmt:message key="label.email" />' value="${email}" required>
				</div>
				<p class="error">${email_error_message}</p>
				<p class="error">${email_exists_error_message}</p>
				<div class="form-group">
					<input type="password" name="password" placeholder='<fmt:message key="label.password" />' required>
				</div>
				<p class="error">${password_error_message}</p>
				<button type="submit" class="btn btn-default">
					<fmt:message key="button.signup" />
				</button>
			</div>
		</form>
	</div>
</div>

<%@ include file="../includes/footer.jsp"%>
