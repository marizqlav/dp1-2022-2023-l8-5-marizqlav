<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="partidas">
    <h2>Turnos</h2>

    <table id="turnosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Ronda</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${partidas}" var="partida">
            <tr>
                <td>
                    <c:out value="${partida.id}"/>
                </td>
                <td>                    
                      <c:out value="${partida.ronda.id} "/>                                        
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/turnos/new">Crear nuevo turno</a>
</idus_martii:layout>