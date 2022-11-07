<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="turnos">
    <h2>Turnos</h2>

    <table id="turnosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Consul</th>
            <th>Predor</th>
            <th>Edil1</th>
            <th>Edil2</th>
            <th>Votos Traidores</th>
            <th>Votos Leales</th>
            <th>Votos Neutrales</th>
            <th>Id de ronda</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${turnos}" var="turno">
            <tr>
                <td>
                    <c:out value="${turno.consul}"/>
                </td>
                <td>                    
                      <c:out value="${turno.predor} "/>                                        
                </td>
                <td>       
                    <c:out value="${turno.edil1} "/>
                </td>
 				<td>       
                    <c:out value="${turno.edil2} "/>
                </td>
                <td>       
                    <c:out value="${turno.votosTraidores} "/>
                </td>
                   <td>       
                    <c:out value="${turno.votosLeales} "/>
                </td>
                   <td>       
                    <c:out value="${turno.votosNeutrales} "/>
                </td>
                   <td>       
                    <c:out value="${turno.ronda.id} "/>
                </td>
                <td> 
                    <a href="/turnos/${turno.id}/edit"> 
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>                            
                    </a>       
                </td>
                <td> 
                    <a href="/turnos/${turno.id}/delete"> 
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>      
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href="/turnos/new">Crear nuevo turno</a>
</idus_martii:layout>