<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>

<!doctype html>
<html>
<idus_martii:htmlHeader/>

<body>
<idus_martii:bodyHeader menuName="${pageName}"/>

<div class="container-fluid">
    <div class="container xd-container">
	<c:if test="${not empty message}" >
	<div class="alert alert-${not empty messageType ? messageType : 'info'}" role="alert">
  		<c:out value="${message}"></c:out>
   		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
  		</button> 
	</div>
	</c:if>

        <jsp:doBody/>

        <idus_martii:pivotal/>
    </div>
</div>
<idus_martii:footer/>
<jsp:invoke fragment="customScript" />

</body>

</html>
