<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/proposal.css"	type="text/css">
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/manager.css"	type="text/css">
	<title><fmt:message key="title.manager.proposals" /></title>
<%@ include file="../../includes/after_head.jsp"%>


<%@ include file="../../includes/before_nav.jsp"%>
	<%@ include file="../../includes/logout.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>


	<div class="container-fluid">
		<div class="row">

			<div class="col-md-4" id="filter-container">
				<br>
				<div class="container filter-div  fixed">
					<form name="filter" action="${pageContext.request.contextPath}/get/manager/managerProposals" method="GET">

						<label> <fmt:message key="label.status" />: </label>
						<select name="status_filter" class="form-control">
							<c:forEach var="status" items="${statuses}">
								<option value="${status}" ${status_filter == status ? 'selected' : ''}>${status}</option>
							</c:forEach>
						</select>

						<div class="form-group">
							<label for="date"> <fmt:message key="label.date" />:	</label>
							<div class="form-inline" id="date">
								<input type="date" name="date_from" class="form-control" placeholder="From" value="${date_from}"> 
								<input type="date"	name="date_to" class="form-control" placeholder="To" value="${date_to}">
							</div>
						</div>

						<button type="submit" class="btn btn-default">
							<fmt:message key="button.filter" />
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

						<p><fmt:message key="label.customer" />: ${proposal.author.name}</p>
						<hr />
						
						<c:if test="${not empty proposal.master}">
							<p><fmt:message key="label.master" />: ${proposal.master.name}</p>
							<hr />
						</c:if>

						<c:if test="${not empty proposal.manager}">
							<p><fmt:message key="label.manager" />: ${proposal.manager.name}</p>
							<hr />
						</c:if>

						<h4><fmt:message key="label.proposal.text" />:</h4>
						<p>${proposal.message}</p>
						<hr />

						<c:if test="${not empty proposal.rejectionCause}">
							<h4><fmt:message key="label.rejection.cause" />:</h4>
							<p>${proposal.rejectionCause}</p>
						</c:if>

						<c:if test="${not empty proposal.price and proposal.price != 0}">
							<h4><fmt:message key="label.price" />: ${proposal.price}</h4>
						</c:if>

						<c:if test='${proposal.status == "NEW"}'>
							<button type="button" onclick="accept()" id="modal-button-accept" class="btn btn-default btn-accept"><fmt:message key="button.accept" /></button>
							<button type="button" onclick="reject()" id="modal-button-reject" class="btn btn-default btn-reject"><fmt:message key="button.reject" /></button>

							<!-- Modal window for acceppting or rejecting proposal -->
							<div class="modal-window">
								<div id="modal-wrapper-accept" class="modal modal-wrapper-accept">
									<div class="modal-content-accept">
										<button type="button" class="close">&times;</button>
										<form action="${pageContext.request.contextPath}/get/manager/accept" method="POST">
											<input type="number" name="price" min="0" placeholder='<fmt:message key="label.price" />' />
											<input type="hidden" name="proposal_id" value="${proposal.id}">
											<input type="hidden" name="manager_id" value="${user.id}">
											<button class="btn btn-default btn-modal" type="submit"><fmt:message key="button.submit" /></button>
										</form>
									</div>
								</div>

								<div id="modal-wrapper-reject" class="modal modal-wrapper-reject">
									<div class="modal-content-reject">
										<button class="close">&times;</button>
										<form action="${pageContext.request.contextPath}/get/manager/reject" method="POST">
											<textarea name="rejection_cause" placeholder='<fmt:message key="label.rejection.cause" />' ></textarea>
											<input type="hidden" name="proposal_id" value="${proposal.id}">
											<input type="hidden" name="manager_id" value="${user.id}">
											<button class="btn btn-default btn-modal"><fmt:message key="button.submit" /></button>
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
						<form action="${pageContext.request.contextPath}/get/manager/managerProposals" method="GET">
							<c:if test="${currentPage > 1}">
						    	<button type="submit" class="btn btn-default button-cool" name="futurePage" value='previous'>
						    		<fmt:message key="button.previous" />
						    </button>
						    </c:if>
						    <c:if test="${currentPage < lastPage}">
							    <button type="submit" class="btn btn-default button-cool" name="futurePage" value='next'>
							    	<fmt:message key="button.next" />
								</button>
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
