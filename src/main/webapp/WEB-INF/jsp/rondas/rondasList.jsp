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
            <th>Id</th>
            <th>Id de partida</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rondas}" var="ronda">
            <tr>
                <td>
                    <c:out value="${ronda.id}"/>
                </td>
                <td>                    
                      <c:out value="${ronda.partidaId} "/>                                        
                </td>
                <td> 
                    <a href="/rondas/${ronda.id}/edit"> 
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>                            
                    </a>       
                </td>
                <td> 
                    <a href="/rondas/${ronda.id}/delete"> 
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>      
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/rondas/new">Crear nueva ronda</a>
</idus_martii:layout>