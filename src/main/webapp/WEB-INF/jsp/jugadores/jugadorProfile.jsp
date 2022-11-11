<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="jugadores">
    <h2>Jugadores</h2>

    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Yo</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
  
            <tr>
                <td>
                    ${jugador.id}
                </td>
                <td>                    
                    ${jugador.user.username}                                       
                </td>
                <td>                    
                    ${currentPlayer.id}                                      
                </td>
            </tr>
   
        </tbody>
    </table>
	<a class="btn btn-default" href="/jugadores/amigos/${currentPlayer.id}/${jugador.id}">Añadir a amigo</a>
</idus_martii:layout>