<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="jugadores">
    <h2>Perfil de usuario</h2>

    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th></th>
            <th>Nombre de usuario</th>
            <th>Nombre</th>
            <th>Apellidos</th>
        </tr>
        </thead>
        <tbody>
  
            <tr>
                <td>                    
                  <c:if test="${jugador.user.photo == ''}">none</c:if>
                  <c:if test="${jugador.user.photo != ''}">
                  	<img src="${jugador.user.photo}" width="100px"  />
                  </c:if>
                </td>
                <td>                    
                    ${jugador.user.username}                                       
                </td>
                <td>                    
                    ${jugador.user.name}                                      
                </td>
                <td>                    
                    ${jugador.user.surname}                                      
                </td>
            </tr>
   
        </tbody>
    </table>
	<a class="btn btn-default" href="/jugadores/amigos/${currentPlayer.id}/${jugador.id}">Añadir a amigo</a>
</idus_martii:layout>