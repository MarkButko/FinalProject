<%@ page isErrorPage="true" %>
<%@ include file="../../includes/before_head.jsp"%>
   <title>Java Exception</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css"   type="text/css">
<%@ include file="../../includes/after_head.jsp"%>
<%@ include file="../../includes/before_nav.jsp"%>
<%@ include file="../../includes/after_nav.jsp"%>

<h1>${pageContext.errorData.throwable.class}</h1>
<p>Status: ${pageContext.errorData.statusCode}</p>
<p>URI: ${pageContext.errorData.requestURI}</p>

<%@ include file="../../includes/footer.jsp"%>