<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Pagina de inicio del juego Idus Martii"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container" >
		<div class="navbar-header">
		
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
				
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<idus_martii:menuItem active="${name eq 'home'}" url="/"
					title="Pagina de inicio">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Pagina principal</span>
				</idus_martii:menuItem>

				<idus_martii:menuItem active="${name eq 'jugadores'}" url="/jugadores/find"
					title="Buscar jugadores">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Buscar jugadores</span>
				</idus_martii:menuItem>

				<idus_martii:menuItem active="${name eq 'Objetos creados'}" url="/turnos"
					title="Objetos creados" dropdown="${true}">										
						<ul class="dropdown-menu">
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/turnos/" />">Lista de turnos</a>		
							</li>
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/rondas/" />">Lista de rondas</a>		
							</li>
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/partida/finalizadas" />">Lista de partidas jugadas</a>		
							</li>
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/partida/enJuego" />">Lista de partidas en juego</a>		
							</li>
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/facciones/" />">Lista de facciones</a>		
							</li>
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/mensajes/" />">Lista de mensajes</a>		
							</li>
							<li class="divider"></li>
							<li>
								<a href="<c:url value="/jugadores/" />">Lista de Jugadores</a>		
							</li>
							
						</ul>					
				</idus_martii:menuItem>	

		
				<idus_martii:menuItem active="${name eq 'achievements'}" url="/statistics/achievements"
					title="Logros" dropdown="${true}">										
						<ul class="dropdown-menu">
							<li>
								<a href="<c:url value="/statistics/achievements/" />">Lista de logros</a>		
							</li>
							<li class="divider"></li>
							<li>								
								<a href="<c:url value="/statistics/achievements/" />">Mis logros <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span></a>		

							</li>
						</ul>					
				</idus_martii:menuItem>	
				
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
