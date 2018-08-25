<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="../../css/filter.css"	type="text/css">
	<link rel="stylesheet"	href="../../css/admin.css"	type="text/css">
	<title>Roles</title>
<%@ include file="../../includes/after_head.jsp"%>

<%@ include file="../../includes/before_nav.jsp"%>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>
	
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-4" id="filter-container">
				<div class="container filter-div  fixed">
					<form name="filter" action="filter">
						<div class="form-group">
							<label for="role-filter"> Role: </label>
							<select name="role_filter" class="form-control">
								<option value="${role}">role</option>
							</select>
						</div>
k
						<div class="form-group">
							<label for="date"> Date of registration:	debug</label>
							<div class="form-inline" id="date">
								<input type="date" name="date_from" class="form-control" placeholder="From" value="${date_from}"> <input type="date"
								name="date_to" class="form-control" placeholder="To" value="${date_to}">
							</div>
						</div>

						<button type="submit" class="btn btn-default">
							submit.filter
						</button>
					</form>
				</div>
			</div> <!-- filter div end-->

			<div class="col-md-8">
				<c:forEach var="user" items="users">
					<div class="user-div">

						<%-- <label class="block">Date of registration: {user.date}</label> --%>
						<label class="block">Name: ${user.name}</label>
						<label class="block">Email: ${user.email}</label>

						<form action="change_role">		
							<label for="role"> Role: </label>
							<select name="role" class="form-control">
								<c:forEach var="role" items="roles">
									<option value="${role}" ${user.role == role ? 'selected' : ''}>${role}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="user_id" value="${user.id}" />
							<button type="submit" id="button-change-role" class="btn btn-default btn-change-role">Change role</button>
						</form>

					</div><!--  user-div end -->
				</c:forEach>
			</div>			
		</div>

	</div>


<%@ include file="../../includes/footer.jsp"%>
