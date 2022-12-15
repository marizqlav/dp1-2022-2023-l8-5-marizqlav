<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<idus_martii:layout pageName="home" >
<style type="text/css" media="screen">
.botona {
	position: relative;
    color: rgba(255,255,255,1);
    text-decoration: none;
    background-color: rgba(50,205,50,1);
    font-family: 'Yanone Kaffeesatz';
    font-weight: 700;
    font-size: 3em;
    display: inline;
    padding: 12px;
    -webkit-border-radius: 8px;
    -moz-border-radius: 8px;
    border-radius: 8px;
    -webkit-box-shadow: 0px 9px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    -moz-box-shadow: 0px 9px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    box-shadow: 0px 9px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
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
    background-color: rgba(50,205,50,1);
    font-family: 'Yanone Kaffeesatz';
    font-weight: 700;
    font-size: 2em;
    display: inline;
    padding: 12px;
    -webkit-border-radius: 8px;
    -moz-border-radius: 8px;
    border-radius: 8px;
    -webkit-box-shadow: 0px 9px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    -moz-box-shadow: 0px 9px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    box-shadow: 0px 9px 0px rgba(0,0,0,1), 0px 9px 25px rgba(0,0,0,.7);
    margin: 170px auto;
	width: 200px;
	text-align: center;
	margin-left:15%;
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

 	    <h2>Perfil de usuario</h2>

    <table id="jugadoresTable" class="table table-striped">
        <thead>
        <tr>
            <th></th>
            <th>Nombre de usuario</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <c:if test="${esTuPerfil || pageContext.request.userPrincipal.authorities=='[admin]'}" >
                  <th>Correo</th>
            </c:if>    
			<th></th>
				


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
               
                <c:if test="${esTuPerfil || pageContext.request.userPrincipal.authorities=='[admin]'}" >
                <td>                
                   	${jugador.user.email}
                </td> </c:if>
                		

                <td> 
                	<c:if test="${esTuPerfil || pageContext.request.userPrincipal.authorities=='[admin]'}" >
                    	<a href="/jugadores/profile/${jugador.id}/edit"> 
                        	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    	</a>
                    </c:if>      
                    <script>
					function myFunction() {
					  let text = "¿Estás seguro de que deseas eliminar este usuario?";
					  if (confirm(text) == true) {
					    window.location.replace("/jugadores/eliminar/${jugador.id}")
					  }
					}
					</script>               
                <sec:authorize access="hasAuthority('admin')">
					<a onclick="myFunction()"  class="botonb">Eliminar usuario</a>
				</sec:authorize>
                </td>
            	

            </tr>
   
        </tbody>
    </table>
   
	<c:if test="${noSonAmigos && jugador.user.username!=pageContext.request.userPrincipal.name}"><a class="btn btn-default" href="/jugadores/amigos/${currentPlayer.id}/${jugador.id}">Añadir a amigo</a></c:if>
</idus_martii:layout>