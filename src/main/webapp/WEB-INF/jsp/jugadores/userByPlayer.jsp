<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="usuario">

	<h2>User</h2>
	
	<table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Photo</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td> 
                	<c:out value="${users.username}"></c:out>
                </td>
                <td> 
                	<c:out value="${users.name}"></c:out>
                </td>
                <td> 
                	<c:out value="${users.surname}"></c:out>
                </td>
                <td> 
                	 <c:if test="${users.photo == ''}">none</c:if>
                     <c:if test="${users.photo != ''}">
                        <img src="${users.photo}" width="100px"  />
                     </c:if>
                </td>
            </tr>
        </tbody>
    </table>
	
	
	
	
</idus_martii:layout>