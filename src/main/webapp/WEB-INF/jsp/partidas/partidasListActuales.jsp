<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="partidas">
    <h2>Partidas En Juego</h2>
    <table id="partidasTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Número de jugadores</th>
            <th>Fecha de creación</th>
            <th>Fecha de inicio</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partidas}" var="partida">
            <tr>
                <td>
                    <c:out value="${partida.id}"/>
                </td>
                 <td>                    
                      <c:out value="${partida.jugador.user.username}"/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.numeroJugadores}"/>                                        
                </td>
                <td>                    
                      <c:out value="${partida.fechaCreacionParseada}"/>                                        
                </td>
                 <td>                    
                      <c:out value="${partida.fechaInicioParseada}"/>                                        
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</idus_martii:layout>