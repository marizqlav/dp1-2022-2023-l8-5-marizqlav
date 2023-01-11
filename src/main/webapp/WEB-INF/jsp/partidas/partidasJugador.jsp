<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="partidasJugador">
    <h2>Historial</h2>

   <table id="partidasTable" class="table table-striped">
        <thead>
        <tr>
            <th>Facción ganadora</th>
            <th>Fin de partida</th>
            <th>Duración</th> 
			<th>Resultado</th>				
        </tr>
        </thead>
        
        <tbody>
  
        <c:forEach items="${partidas.keySet()}" var="partida">
            <tr>
             	<td>${partida.faccionGanadora}</td>
	            <td>${partida.getFechaFin()}</td>
	            <td>${partida.getDuration()[0]}h ${partida.getDuration()[1]}m ${partida.getDuration()[2]}s</td> 
				<td>${partidas.get(partida)}</td>	
            </tr>
        </c:forEach>
   
        </tbody>
    </table>
   
   
   <a  class="btn btn-default" href="/jugadores/profile/${jugador}">Perfil</a>
   
   <table>
		<c:if test="${pagina > 1}">
			<td><a class="btn btn-default" href="/partida/partidas/jugador/${jugador}/1"> 1 </a></td>
			<td><a class="btn btn-default" href="/partida/partidas/jugador/${jugador}/${pagina -1}"><</a></td>
		</c:if>
		<td><a class="btn btn-default">${pagina}</a></td>
		<c:if test="${pagina < max-1}">
			<td><a class="btn btn-default" href="/partida/partidas/jugador/${jugador}/${pagina +1}">></a></td>
			<td><a class="btn btn-default" href="/partida/partidas/jugador/${jugador}/${max}"> ${max} </a></td>
		</c:if>
		</table>
</idus_martii:layout>