<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="../../css/proposal.css"	type="text/css">
	<link rel="stylesheet"	href="../../css/master.css"	type="text/css">
	<title>Users' proposals</title>
<%@ include file="../../includes/after_head.jsp"%>


<%@ include file="../../includes/before_nav.jsp"%>
	<div class="menu">
		<ul class="nav navbar-nav">
			<li><a href="#" class="custom-underline active">Proposals</a></li>
			<li><a href="#" class="custom-underline">Comments</a></li>
			<hr />
		</ul>
	</div>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4" id="filter-container">
				<br>
				<div class="container filter-div  fixed">
					<form name="filter" action="filter">
						<div class="form-group">
							<label for="date"> Date:	</label>
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
			</div>



			<div class="col-md-8">
				<cstm:proposals>
					<div class="proposal-div">
						<label class="date">${date}</label>
						<label>${status}</label>
						<hr />

						<p>Author: ${author.name}</p>
						<p>Manager: ${manager.name}</p>
						<hr />

						<h4>Proposal text:</h4>
						<p>${message}</p>
						<hr />

						<h4>Price: ${price}</h4>

						<c:if test='${status.name eq "ACCEPTED"}'>
							<form action="perform">						
								<button type="submit" id="button-perform" class="btn btn-default btn-perform">Perform</button>
							</form>
						</c:if>
					</div><!--  proposal-div end -->
				</cstm:proposals>
			</div>			
		</div>
	</div>

<%@ include file="../../includes/footer.jsp"%>