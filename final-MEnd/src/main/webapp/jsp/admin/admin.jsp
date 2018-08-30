<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/filter.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/admin.css"	type="text/css">
	<title>Roles</title>
<%@ include file="../../includes/after_head.jsp"%>

<%@ include file="../../includes/before_nav.jsp"%>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>
	
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-4">
				<div class="container filter-div  fixed">
					<form name="filter" action="${pageContext.request.contextPath}/get/admin/users" method="POST">
						<div class="form-group">
							<label> Role: </label>
							<select name="role" class="form-control">
								<c:forEach var="role" items="${roles}">
									<c:if test="${role != 'GUEST'}">
										<option value="${role}">${role}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label for="date">Date of registration:</label>
							<div class="form-inline" id="date">
								<input type="date" name="date_from" class="form-control" placeholder="From" value="${date_from}"> 
								<input type="date" name="date_to" class="form-control" placeholder="To" value="${date_to}">
							</div>
						</div>

						<button type="submit" class="btn btn-default">
							button.filter
						</button>
					</form>
				</div>

				<div class="container sort-div fixed">
					<form name="sort" action="${pageContext.request.contextPath}/get/admin/users" method="POST">
						<div class="form-group">
							<select name="sort-type" class="form-control">
								<option value="email">sort.type.email</option>
								<option value="date">sort.type.date</option>
								<option value="name">sort.type.name</option>
							</select>
						</div>

						<button type="submit" class="btn btn-default">
							button.sort
						</button>
					</form>
				</div>
			</div> 

			<div class="col-md-8">
				<c:forEach var="u" items="${users}">
					<c:if test="${(not empty u) and user.id != u.id}">
						<div class="user-div">

							<label class="block">Date of registration: ${u.registrationDate}</label>
							<label class="block">Name: ${u.name}</label>
							<label class="block">Email: ${u.email}</label>

							<form action="${pageContext.request.contextPath}/get/admin/change_role" method="POST">		
								<label> Role: </label>
								<select name="role" class="form-control">
									<c:forEach var="role" items="${roles}">
										<option value="${role}" ${u.role == role ? 'selected' : ''}>${role}</option>
									</c:forEach>
								</select>
								<input type="hidden" name="id" value="${u.id}" />
								<button type="submit" id="button-change-role" class="btn btn-default btn-change-role">Change role</button>
							</form>

						</div><!--  user-div end -->
					</c:if>
				</c:forEach>
			</div>			
		</div>

	</div>


<%@ include file="../../includes/footer.jsp"%>
