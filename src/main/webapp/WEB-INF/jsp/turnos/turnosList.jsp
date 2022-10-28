<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="turnos">
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
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>