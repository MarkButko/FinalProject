<%@ include file="../../includes/before_head.jsp"%>
	<link rel="stylesheet"	href="../../css/proposal.css"	type="text/css">
	<link rel="stylesheet"	href="../../css/manager.css"	type="text/css">
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

						<label for="status-filter"> Status: </label>
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
			<cstm:proposals>
				<div class="proposal-div">
					<label class="date">${date}</label>
					<label>${status}</label>
					<hr />

					<p>Author: ${author.name}</p>
					<hr />
					
					<c:if test="${not empty master}">
						<p>Master: ${master.name}</p>
						<hr />
					</c:if>

					<h4>Proposal text:</h4>
					<p>${message}</p>
					<hr />

					<c:if test="${not empty rejection_cause}">
						<h4>Rejection cause:</h4>
						<p>${rejection_cause}</p>
					</c:if>

					<c:if test="${not empty price}">
						<h4>Price: ${price}</h4>
					</c:if>

					<c:if test='${status.name eq "NEW"}'>
						<button type="button" onclick="accept()" id="modal-button-accept" class="btn btn-default btn-accept">Accept</button>
						<button type="button" onclick="reject()" id="modal-button-reject" class="btn btn-default btn-reject">Reject</button>

						<!-- Modal window for acceppting or rejecting proposal -->
						<div class="modal-window">
							<div id="modal-wrapper-accept" class="modal modal-wrapper-accept">
								<div class="modal-content-accept">
									<button type="button" class="close">&times;</button>
									<form action="accept" method="POST">
										<input type="text" name="price" min="0" placeholder="Price" />
										<button class="btn btn-default btn-modal">Submit</button>
									</form>
								</div>
							</div>

							<div id="modal-wrapper-reject" class="modal modal-wrapper-reject">
								<div class="modal-content-reject">
									<button class="close">&times;</button>
									<form action="reject" method="POST">
										<textarea name="rejection_cause" placeholder="Rejection cause" ></textarea>
										<button class="btn btn-default btn-modal">Submit</button>
									</form>
								</div>
							</div>
						</div>	
					</c:if>
				</div><!--  proposal-div end -->	
			</cstm:proposals>
		</div>			
	</div> <!-- row div end-->
</div>

<script type="text/javascript" src="../../script/modal.js"></script>

<%@ include file="../../includes/footer.jsp"%>
