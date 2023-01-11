<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="partidas">
    <h2>Detalles de partida</h2>
<p><c:out value="${now}"/></p>
    <table id="turnosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Creador</th>
            <th>Facción Ganadora</th>
            <th>Número de jugadores</th>
            <th>Fecha de creación</th>
            <th>Fecha de inicio</th>
            <th>Fecha de finalización</th>
            <th>Duración</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <c:out value="${partida.id}"/>
                </td>
                <td>                    
                      <a href="/jugadores/profile/${partida.jugador.id}" target="_blank"><c:out value="${partida.jugador.user.username}"/></a>                                      
                </td>
                 <td> 
                	<c:choose>
                 		<c:when test="${partida.faccionGanadora!=null}">	               
                     		<c:out value="${partida.faccionGanadora}"/>       
                   		</c:when>    
    					<c:otherwise>
    						No determinada
                    	</c:otherwise>
					</c:choose>                               
                </td>
                <td>                    
                      <c:out value="${partida.numeroJugadores}"/>                                        
                </td>
                <td>                   
                    <fmt:parseDate value="${ partida.fechaCreacion }" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ parsedDateTime }" />                                          
                </td>
                 <td>
                 	<c:choose>
                 		<c:when test="${partida.fechaInicio!=null}">	               
                     		<fmt:parseDate value="${ partida.fechaInicio }" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ parsedDateTime }" />         
                   		</c:when>    
    					<c:otherwise>
    						No determinada
                    	</c:otherwise>
					</c:choose>                                                             
                </td>
                <td>
                	<c:choose>
                 		<c:when test="${partida.fechaFin!=null}">	               
                     		<fmt:parseDate value="${ partida.fechaFin }" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
							<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${ parsedDateTime }" />         
                   		</c:when>    
    					<c:otherwise>
    						No determinada
                    	</c:otherwise>
					</c:choose>                                                           
                </td>
				<td>
                	<c:choose>
                 		<c:when test="${partida.fechaFin!=null}">	               
                     		<c:out value="${partida.duration[0]}"/> horas 
                      		<c:out value="${partida.duration[1]}"/> minutos
                      		<c:out value="${partida.duration[2]}"/> segundos        
                   		</c:when>    
    					<c:otherwise>
    						No determinada
                    	</c:otherwise>
					</c:choose>                                                           
                </td>

            </tr>
        </tbody>
    </table>
    <br>
    <h2>Jugadores</h2>
    <table id="turnosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Foto de perfil</th>
            <th>Username</th>
            <th>Facción posible 1</th>
            <th>Facción posible 2</th>
            <th>Facción elegida</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${datospartida}" var="datopartida">
            <tr>
                <td>
                    <img src="${datopartida.jugador.user.photo}" width="100" height="100"/>
                </td>
                <td>                    
                      <a href="/jugadores/profile/${datopartida.jugador.id}" target="_blank"><c:out value="${datopartida.jugador.user.username}"/></a>                                      
                </td>
                 <td>                    
                      <c:out value="${datopartida.faccionPosible1}"/><br>
                      <c:choose>
                      	<c:when test="${datopartida.faccionPosible1=='Leal'}">
                      		<img src="/resources/images/faccion_leal.jpg" width="50"/>
                      	</c:when>
                      	<c:when test="${datopartida.faccionPosible1=='Traidor'}">
                      		<img src="/resources/images/faccion_traidor.jpg" width="50"/>
                      	</c:when>
                      	<c:when test="${datopartida.faccionPosible1=='Mercader'}">
                      		<img src="/resources/images/faccion_mercader.jpg" width="50"/>
                      	</c:when>   
                      </c:choose>                                  
                </td>
                <td>                    
                      <c:out value="${datopartida.faccionPosible2}"/><br>
                       <c:choose>
                      	<c:when test="${datopartida.faccionPosible2=='Leal'}">
                      		<img src="/resources/images/faccion_leal.jpg" width="50"/>
                      	</c:when>
                      	<c:when test="${datopartida.faccionPosible2=='Traidor'}">
                      		<img src="/resources/images/faccion_traidor.jpg" width="50"/>
                      	</c:when>
                      	<c:when test="${datopartida.faccionPosible2=='Mercader'}">
                      		<img src="/resources/images/faccion_mercader.jpg" width="50"/>
                      	</c:when>   
                      </c:choose>                                    
                </td>
                 <td> 
                	<c:choose>
                 		<c:when test="${datopartida.faccionSelecionada!=null}">	               
                     		<c:out value="${datopartida.faccionSelecionada}"/>       
                   		</c:when>    
    					<c:otherwise>
    						No determinada
                    	</c:otherwise>
					</c:choose><br>
					 <c:choose>
                      	<c:when test="${datopartida.faccionSelecionada=='Leal'}">
                      		<img src="/resources/images/faccion_leal.jpg" width="50"/>
                      	</c:when>
                      	<c:when test="${datopartida.faccionSelecionada=='Traidor'}">
                      		<img src="/resources/images/faccion_traidor.jpg" width="50"/>
                      	</c:when>
                      	<c:when test="${datopartida.faccionSelecionada=='Mercader'}">
                      		<img src="/resources/images/faccion_mercader.jpg" width="50"/>
                      	</c:when>
                      	<c:otherwise>
    						<img src="/resources/images/idus-martii.jpg" width="50"/>
                    	</c:otherwise>  
                      </c:choose>                              
                </td>
            </tr>
          </c:forEach>
        </tbody>
    </table>
</idus_martii:layout>