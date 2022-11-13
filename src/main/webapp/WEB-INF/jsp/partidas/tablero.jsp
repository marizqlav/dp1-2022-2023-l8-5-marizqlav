<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="rondas">
    <h2>Rondas</h2>

    <table id="rondasTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id de partida</th>
            <th>Id de ronda</th>
            <th>Id de turno</th>
            <th>Consul</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partida}" var="ronda">
            <tr>
                <td>
                    <c:out value="${partida.id}"/>
                </td>
                <td>                    
                      <c:out value="${partida.ronda.id} "/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.ronda.turno.id} "/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.ronda.turno.consul.user.username} "/>                                        
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/rondas/new">Crear nueva ronda</a>
</idus_martii:layout>