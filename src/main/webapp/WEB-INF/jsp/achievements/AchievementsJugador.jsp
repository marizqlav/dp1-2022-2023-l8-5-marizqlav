<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<idus_martii:layout pageName="jugadores">
    <h2>Logros</h2>	
    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
        <th></th>
        	<th>Logro</th>
            <th>Descripción</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
            	<td>                    
                  <a><img src="${achievement.badgeImage}" width="100px" height="100px"  /></a> 
                </td>
            	<td>                    
                  <a>${achievement.name}</a> 
                </td>
                <td>                    
                      <a><c:out value="${achievement.getActualDescription()}"/></a>                                       
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href="/jugadores/profile/${jugador}">Ver perfil</a>	
		
</idus_martii:layout>