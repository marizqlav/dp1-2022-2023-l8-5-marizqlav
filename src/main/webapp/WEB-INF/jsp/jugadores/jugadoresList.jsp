<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="jugadores">
    <h2>Jugadores</h2>

    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre de usuario</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="jugador">
            <tr>
             	<td>                    
                  <c:if test="${jugador.user.photo == ''}">Sin foto de perfil</c:if>
                  <c:if test="${jugador.user.photo != ''}">
                  	<img src="${jugador.user.photo}" width="100px" height="100px"  />
                  </c:if>
                </td>
                <td>                    
                      <a href="/jugadores/profile/${jugador.id}"><c:out value="${jugador.user.username} "/> </a>                                       
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/rondas/new">Crear nuevo jugador</a>
</idus_martii:layout>