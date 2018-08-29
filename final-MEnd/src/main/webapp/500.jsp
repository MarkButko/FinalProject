<%@ page isErrorPage="true" %>
<%@ include file="../../includes/before_head.jsp"%>
   <title>Java Exception</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css"   type="text/css">
<%@ include file="../../includes/after_head.jsp"%>
<%@ include file="../../includes/before_nav.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>

<h1>${exception_type}</h1>
<p>Status: ${status_code}</p>
<p>Servlet: ${servlet_name}</p>

<%@ include file="../../includes/footer.jsp"%>