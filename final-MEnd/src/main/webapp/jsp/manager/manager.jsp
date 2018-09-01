<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/proposal.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/manager.css"	type="text/css">
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
					<form name="filter" action="${pageContext.request.contextPath}/get/manager/acceptedProposals" method="POST">

						<label> Status: </label>
						<select name="status_filter" class="form-control">
							<option value="${status}">status</option>
						</select>

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
			</div> <!-- filter container end -->


		<div class="col-md-8">
			<c:forEach var="proposal" items="${requestScope.proposals}">
				<c:if test="${not empty proposal}">
					<div class="proposal-div">
						<label class="date">${proposal.date}</label>
						<label>${proposal.status}</label>
						<hr />

						<p>Author: ${proposal.author.name}</p>
						<hr />
						
						<c:if test="${not empty master}">
							<p>Master: ${proposal.master.name}</p>
							<hr />
						</c:if>

						<h4>Proposal text:</h4>
						<p>${proposal.message}</p>
						<hr />

						<c:if test="${not empty rejection_cause}">
							<h4>Rejection cause:</h4>
							<p>${proposal.rejection_cause}</p>
						</c:if>

						<c:if test="${not empty price}">
							<h4>Price: ${proposal.price}</h4>
						</c:if>

						<c:if test='${proposal.status == "NEW"}'>
							<button type="button" onclick="accept()" id="modal-button-accept" class="btn btn-default btn-accept">Accept</button>
							<button type="button" onclick="reject()" id="modal-button-reject" class="btn btn-default btn-reject">Reject</button>

							<!-- Modal window for acceppting or rejecting proposal -->
							<div class="modal-window">
								<div id="modal-wrapper-accept" class="modal modal-wrapper-accept">
									<div class="modal-content-accept">
										<button type="button" class="close">&times;</button>
										<form action="${pageContext.request.contextPath}/get/manager/accept" method="POST">
											<input type="text" name="price" min="0" placeholder="Price" />
											<button class="btn btn-default btn-modal">Submit</button>
										</form>
									</div>
								</div>

								<div id="modal-wrapper-reject" class="modal modal-wrapper-reject">
									<div class="modal-content-reject">
										<button class="close">&times;</button>
										<form action="${pageContext.request.contextPath}/get/manager/reject" method="POST">
											<textarea name="rejection_cause" placeholder="Rejection cause" ></textarea>
											<button class="btn btn-default btn-modal">Submit</button>
										</form>
									</div>
								</div>
							</div>	
						</c:if>
					</div><!--  proposal-div end -->	
				</c:if>
			</c:forEach>

			<div class="row">
					<div class="col-md-offset-4 col-md-4 pagination-div">
						<form action="${pageContext.request.contextPath}/get/manager/acceptedProposals" method="POST">
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
	</div> <!-- row div end-->
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/modal.js"></script>

<%@ include file="../../includes/footer.jsp"%>
