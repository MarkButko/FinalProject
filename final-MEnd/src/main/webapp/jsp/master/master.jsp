<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/proposal.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/master.css"	type="text/css">
	<title>Users' proposals</title>
<%@ include file="../../includes/after_head.jsp"%>


<%@ include file="../../includes/before_nav.jsp"%>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4" id="filter-container">
				<br>
				<div class="container filter-div  fixed">
					<form name="filter" action="${pageContext.request.contextPath}/get/master/acceptedProposals">
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
				<c:forEach var="proposal" items="${requestScope.proposals}">
						<div class="proposal-div">
							<label class="date">${proposal.date}</label>
							<label>${proposal.status}</label>
							<hr />

							<p>Author: ${proposal.author.name}</p>
							<p>Manager: ${proposal.manager.name}</p>
							<hr />

							<h4>Proposal text:</h4>
							<p>${proposal.message}</p>
							<hr />

							<h4>Price: ${proposal.price}</h4>
							
							<form action="${pageContext.request.contextPath}/get/master/perform" method="POST">	
								<input type="hidden" name="proposal_id" value="${proposal.id}">	
								<input type="hidden" name="author_id" value="${proposal.author.id}">	
								<input type="hidden" name="master_id" value="${user.id}">	
								<button type="submit" id="button-perform" class="btn btn-default btn-perform">Perform</button>
							</form>
							
						</div><!--  proposal-div end -->
				</c:forEach>

				<div class="row">
					<div class="col-md-offset-4 col-md-4 pagination-div">
						<form action="${pageContext.request.contextPath}/get/master/acceptedProposals" method="POST">
							<c:if test="${currentPage > 1}">
						    	<input type="submit" class="btn btn-default button-cool" name="futurePage" value="previous">
						    </c:if>
						    <c:if test="${currentPage < lastPage}">
						    	<input type="submit" class="btn btn-default button-cool" name="futurePage" value="next">
							</c:if>
							<input type="hidden" name = "currentPage" value="${requestScope.currentPage}">
						</form>
					</div>
				</div>
			
			</div>			
		</div>
	</div>

<%@ include file="../../includes/footer.jsp"%>