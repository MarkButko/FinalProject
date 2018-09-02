<%@ include file="../includes/before_head.jsp"%>
<title><fmt:message key="title.login" /></title>
<%@ include file="../includes/after_head.jsp"%>
<%@ include file="../includes/before_nav.jsp"%>
<%@ include file="../includes/after_nav.jsp"%>

<div class="container" id="login-div">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form id="login-form" action="${pageContext.request.contextPath}/get/login" class="centered" method="POST">
				<div class="form-group">
					<input type="email" name="email" placeholder='<fmt:message key="label.email" />' value="${email}" required>
				</div>
				<c:if test="${not empty email_error_message}">
					<p class="error">${email_error_message}</p>
				</c:if>
				<div class="form-group">
					<input type="password" name="password" placeholder='<fmt:message key="label.password" />' required>
				</div>
				<c:if test="${not empty password_error_message}">
					<p class="error">${password_error_message}</p>
				</c:if>
				<c:if test="${not empty login_error_message}">
					<p class="error">${login_error_message}</p>
				</c:if>
				<button type="submit" class="btn btn-default">
					<fmt:message key="button.login" />
				</button>
		</div>
		</form>
	</div>
</div>

<div id="redirect-container" class="container">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<div id="redirect">
				<label><fmt:message key="label.signup" /></label>
				 <a id="sign-up-a" href="${pageContext.request.contextPath}/get/sign-up">
				 	<fmt:message key="button.signup" />
				 </a>
			</div>
		</div>
	</div>
</div>

<%@ include file="../includes/footer.jsp"%>