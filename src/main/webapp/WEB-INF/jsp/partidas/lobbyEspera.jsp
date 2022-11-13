<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="lobby">
    <h2>Lobby de espera</h2>

    <table id="lobbyTable" class="table table-striped">
        <thead>
        <tr>
            <th>Estado</th>
            <th>Jugador</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${jugadores}" var="jugador">
            <tr>
                <td>
                   Conectado
                </td>
                <td>
                    <a href="/jugadores/profile/${jugador.id}" target="_blank"><c:out value="${jugador.user.username}"/></a>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/rondas/new">Crear nueva ronda</a>
</idus_martii:layout>