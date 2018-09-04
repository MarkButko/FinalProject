<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/filter.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/admin.css"	type="text/css">
	<title><fmt:message key="title.admin.users" /></title>
<%@ include file="../../includes/after_head.jsp"%>

<%@ include file="../../includes/before_nav.jsp"%>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>
	
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-4">
				<div class="container filter-div  fixed">
					<form name="filter" action="${pageContext.request.contextPath}/get/admin/users" method="GET">
						<div class="form-group">
							<label> <fmt:message key="label.role" />: </label>
							<select name="role" class="form-control">
								<c:forEach var="role" items="${roles}">
									<option value="${role}">${role}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label for="date"><fmt:message key="label.registration.date" />:</label>
							<div class="form-inline" id="date">
								<input type="date" name="date_from" class="form-control" placeholder="From" value="${date_from}"> 
								<input type="date" name="date_to" class="form-control" placeholder="To" value="${date_to}">
							</div>
						</div>

						<button type="submit" class="btn btn-default">
							<fmt:message key="button.filter" />
						</button>
					</form>
				</div>

				<div class="container sort-div fixed">
					<form name="sort" action="${pageContext.request.contextPath}/get/admin/users" method="GET">
						<div class="form-group">
							<select name="sort-type" class="form-control">
								<option value="email"><fmt:message key="option.sort.email" /></option>
								<option value="date"><fmt:message key="option.sort.date" /></option>
								<option value="name"><fmt:message key="option.sort.name" /></option>
							</select>
						</div>

						<button type="submit" class="btn btn-default">
							<fmt:message key="button.sort" />
						</button>
					</form>
				</div>
			</div> 

			<div class="col-md-8">
				<c:forEach var="u" items="${users}">
					<c:if test="${(not empty u) and user.id != u.id}">
						<div class="user-div">

							<label class="block"><fmt:message key="label.registration.date" />: ${u.registrationDate}</label>
							<label class="block"><fmt:message key="label.name" />: ${u.name}</label>
							<label class="block"><fmt:message key="label.email" />: ${u.email}</label>

							<form action="${pageContext.request.contextPath}/get/admin/change_role" method="POST">		
								<label> <fmt:message key="label.role" />: </label>
								<select name="role" class="form-control">
									<c:forEach var="role" items="${roles}">
										<option value="${role}" ${u.role == role ? 'selected' : ''}>${role}</option>
									</c:forEach>
								</select>
								<input type="hidden" name="id" value="${u.id}" />
								<button type="submit" id="button-change-role" class="btn btn-default btn-change-role"><fmt:message key="button.change.role" /></button>
							</form>

						</div><!--  user-div end -->
					</c:if>
				</c:forEach>
			</div>			
		</div>

	</div>


<%@ include file="../../includes/footer.jsp"%>
