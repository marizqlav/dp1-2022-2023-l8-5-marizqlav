<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="partidas">
    <h2>Partidas</h2>
<p><c:out value="${now}"/></p>
    <table id="turnosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Facción Ganadora</th>
            <th>Número de jugadores</th>
            <th>Fecha de creación</th>
            <th>Fecha de inicio</th>
            <th>Fecha de finalización</th>
            <th>Duración</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partidas}" var="partida">
            <tr>
                <td>
                    <c:out value="${partida.id}"/>
                </td>
                 <td>                    
                      <c:out value="${partida.actualFaccionGanadora}"/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.numeroJugadores}"/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.fechaCreacion}"/>                                        
                </td>
                 <td>                    
                      <c:out value="${partida.fechaInicio}"/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.fechaFin}"/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.duration}"/>                                        
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/turnos/new">Crear nuevo partida</a>
</idus_martii:layout>