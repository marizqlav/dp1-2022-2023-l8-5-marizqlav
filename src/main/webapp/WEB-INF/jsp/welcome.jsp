<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div style="background-image: url(/resources/images/portada.jpg); background-size: 100% 100%; height:120%">
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

 	<sec:authorize access="!hasAuthority('admin')">
		<a href="/partida/new" class="botona">Crear Partida</a>
		<a href="/partida/disponibles" class="botona">Unirse a Partida</a>
	</sec:authorize>
	<sec:authorize access="hasAuthority('admin')">
		<a href="/partida/enJuego" class="botonb">Lista de partidas en curso</a>
		<a href="/partida/finalizadas" class="botonb">Lista de partidas terminadas</a>
	</sec:authorize>
<!--
    <h2><fmt:message key="welcome"/></h2>
   	<div class="row">
   	
    <h2>Project ${title}</h2>
    <p><h2>Group ${group}</h2></p>
    <p><ul>
    <c:forEach items="${persons}" var="person">
    	<li>${person.firstName}<h></h> ${person.lastName}</li>
    </c:forEach>    
    </ul></p>  
    </div>
    -->  
    <div class="row">
    	<div class="col-md-12">
<!-- 
            <spring:url value="/resources/images/portada.jpg" htmlEscape="true" var="usImage"/>
            <img class="img-responsive" src="${usImage}"/>
-->
        </div>
    </div>
   
</idus_martii:layout>
 </div>