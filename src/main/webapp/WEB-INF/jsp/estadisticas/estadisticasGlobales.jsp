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

	<h2>Estadístcas Globales</h2>    
		<div>
			<table id="jugadoresTable" class="table table-striped">
	        <thead>
		        <tr>
		
		            <th>Partidas jugadas</th>
		            <th>Máximo de jugadores</th>
		            <th>Mínimo de jugadores</th>
		            <th>Jugadores Promedio</th>
		            <th>Duración Promedio</th>
		            <th>Facción con más victorias</th>
		        </tr>
	        </thead>
	        <tbody>
            	<tr>
	                <td>                    
		                  <c:if test="${totalPartidas == null}">0</c:if>
		                  <c:if test="${totalPartidas != null}">
              						${totalPartidas}
		                  </c:if>
	                </td>
	                <td>  
	                	<c:if test="${max == null}">0</c:if>
	                  	<c:if test="${max != null}">
	                  		${max} 
	                  	</c:if>                               
	                </td>
	                <td>
	                	<c:if test="${min == null}">0</c:if>
	                  	<c:if test="${min != null}">
	                  		${min} 
	                  	</c:if>                                     
	                </td>
	                <td>   
	                	<c:if test="${media == null}">0</c:if>
	                  	<c:if test="${media != null}">
	                  		${media} 
	                  	</c:if>                                     
	                </td>
	                <td>   
	                	<c:if test="${mediaDuracion == null}">0</c:if>
	                  	<c:if test="${mediaDuracion != null}">
	                  		${mediaDuracion[0]}h ${mediaDuracion[1]} m ${mediaDuracion[2]} s  
	                  	</c:if>                                     
	                </td>
	                <td>   
	                	<c:if test="${faccionMasGanadora == null}">Paz en el senado</c:if>
	                  	<c:if test="${faccionMasGanadora != null}">
	                  		${faccionMasGanadora}
	                  	</c:if>                                     
	                </td>
           		</tr>
	        </tbody>
			</table>
		</div>
		<div>
			<h3>Partida más Larga</h3>
			<table id="partidasTable" class="table table-striped">
		   		<thead>
		   			<tr>
		   				<th>Facción ganadora</th>
		   				<th>Número de jugadores</th>
		       			<th>Duración</th>
		   			</tr>
		   		</thead>
			     <tbody>
			         <tr>
			          	<td>
			               ${larga.getFaccionGanadora()}
			             </td>
			             <td>                    
		                    ${larga.getNumeroJugadores()}                                    
			             </td>
			             <td>                    
		                    ${larga.getDuration()[0]}h ${larga.getDuration()[1]} m ${larga.getDuration()[2]} s                                   
			             </td>
			         </tr>
			     </tbody>
			</table>
	   	</div>
	   	
	   	<div>
			<h3>Partida más corta</h3>
			<table id="partidasTable" class="table table-striped">
			   		<thead>
			   			<tr>
			   				<th>Facción ganadora</th>
			   				<th>Número de jugadores</th>
			       			<th>Duración</th>
			   			</tr>
			   		</thead>
				     <tbody>
				         <tr>
				          	<td>
				               ${corta.getFaccionGanadora()}
				             </td>
				             <td>                    
			                    ${corta.getNumeroJugadores()}                                    
				             </td>
				             <td>                    
			                    ${corta.getDuration()[0]}h ${corta.getDuration()[1]} m ${corta.getDuration()[2]} s                                   
				             </td>
				         </tr>
				     </tbody>
				</table>
	   	</div>
   
		<div>
			<h3>Ultimas 6 Paridas</h3>
			<table id="partidasTable" class="table table-striped">
		   		<thead>
		   			<tr>
		   				<th>Facción ganadora</th>
		   				<th>Número de jugadores</th>
		       			<th>Duración</th>
		   			</tr>
		   		</thead>
			     <tbody>
				     <c:forEach items="${partidas}" var="partida">
				         <tr>
				          	<td>
				               ${partida.getFaccionGanadora()}
				             </td>
				             <td>                    
				                    ${partida.getNumeroJugadores()}                                    
				             </td>
				             <td>                    
				                    ${partida.getDuration()[0]}h ${partida.getDuration()[1]} m ${partida.getDuration()[2]} s                                   
				             </td>
				         </tr>
				     </c:forEach>
			     </tbody>
			</table>
   	</div>
	
</idus_martii:layout>