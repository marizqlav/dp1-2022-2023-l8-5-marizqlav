<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css" media="screen">
.botona {
	position: relative;
    color: rgba(255,255,255,1);
    text-decoration: none;
    background-color: rgba(50,205,50,1);
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

.botona:active {
    -webkit-box-shadow: 0px 3px 0px rgba(0,0,0,1), 0px 3px 6px rgba(0,0,0,.9);
    -moz-box-shadow: 0px 3px 0px rgba(0,0,0,1), 0px 3px 6px rgba(0,0,0,.9);
    box-shadow: 0px 3px 0px rgba(0,0,0,1), 0px 3px 6px rgba(0,0,0,.9);
    position: relative;
    text-decoration:none;
    top: 6px;
}

.botona:hover {
 text-decoration:none;
 color:rgba(0,0,0,1);
}
.botona:focus {
  text-decoration:none;
 color:rgba(0,0,0,1);
}

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

.centrado{
	margin-top:30px;
}
</style>


<idus_martii:layout pageName="peticiones">
    <h2>Peticiones de amistad</h2>
    <table id="turnosTable" class="table table-striped" >
        <thead>
        <tr>
			<th></th>
            <th>Jugador</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${peticiones}" var="peticion">
   			<tr>
   				<td>                    
                  <c:if test="${jugador.user.photo == ''}">Sin foto de perfil</c:if>
                  <c:if test="${jugador.user.photo != ''}">
                  	<a href="/jugadores/profile/${peticion.id}" target="_blank"><img src="${peticion.user.photo}" width="100px" height="100px" /></a>  
                  </c:if>
                </td>    
                <td>
                	<div class="centrado">
                    	<a href="/jugadores/profile/${peticion.id}" target="_blank"><c:out value="${peticion.user.username}"/></a>
                    </div>
                </td>
                <td> 
                	<div class="centrado">                   
                      	<a href="/jugadores/peticiones/aceptar/${peticion.id}" class="botona">Aceptar</a>        
                     </div>                              
                </td>
                <td>
                	<div class="centrado">                   
                      <a href="/jugadores/peticiones/rechazar/${peticion.id}" class="botonb">Rechazar</a>
                     </div>                                      
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</idus_martii:layout>