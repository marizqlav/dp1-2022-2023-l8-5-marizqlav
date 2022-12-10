<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
.botonb {
	position: relative;
    color: rgba(255,255,255,1);
    text-decoration: none;
    background-color: rgba(255,0,0,1);
    font-family: 'Yanone Kaffeesatz';
    font-weight: 700;
    font-size: 1em;
    display: inline;
    padding: 5px;
    -webkit-border-radius: 8px;
    -moz-border-radius: 8px;
    border-radius: 8px;
    -webkit-box-shadow: 0px 6px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    -moz-box-shadow: 0px 6px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    box-shadow: 0px 6px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    margin: 170px auto;
	width: 200px;
	text-align: center;
	margin-left:18%;
	-webkit-transition: all .1s ease;
	-moz-transition: all .1s ease;
	-ms-transition: all .1s ease;
	-o-transition: all .1s ease;
	transition: all .1s ease;
}

.botonb:active {
    -webkit-box-shadow: 0px 3px 0px rgba(0,0,0,1), 0px 3px 6px rgba(0,0,0,.9);
    -moz-box-shadow: 0px 3px 0px rgba(0,0,0,1), 0px 3px 6px rgba(0,0,0,.9);
    box-shadow: 0px 3px 0px rgba(0,0,0,1), 0px 3px 6px rgba(0,0,0,.9);
    position: relative;
    text-decoration:none;
    top: 6px;
}

.botonb:hover {
 text-decoration:none;
 color:rgba(0,0,0,1);
}
.botonb:focus {
  text-decoration:none;
 color:rgba(0,0,0,1);
}
</style>
<idus_martii:layout pageName="jugadores">
    <h2>Mis amigos</h2>

    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
        	<th></th>
            <th>Nombre de usuario</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="jugador">
            <tr>
             	<td>                    
                  <c:if test="${jugador.user.photo == ''}">Sin foto de perfil</c:if>
                  <c:if test="${jugador.user.photo != ''}">
                  	<a href="/jugadores/profile/${jugador.id}"><img src="${jugador.user.photo}" width="100px" height="100px"  /></a>  
                  </c:if>
                </td>
                <td>                    
                      <a href="/jugadores/profile/${jugador.id}"><c:out value="${jugador.user.username} "/> </a>                                       
                </td>
                <td>
                	<a href="/jugadores/amigos/eliminar/${jugador.id}" class="botonb">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</idus_martii:layout>
