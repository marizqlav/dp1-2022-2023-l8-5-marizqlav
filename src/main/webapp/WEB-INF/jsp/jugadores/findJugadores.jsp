<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<idus_martii:layout pageName="jugadores">

    <h2>Buscar Jugadores</h2>

    
    <form:form modelAttribute="jugador" action="/jugadores" method="get" class="form-horizontal"
               id="search-owner-form">
        <div class="form-group">
            <div class="control-group" id="username">
                <label class="col-sm-2 control-label">Username</label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="user.username" size="30" maxlength="80"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Buscar Jugador</button>
            </div>
        </div>

    </form:form>

    <br/> 
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/jugadores/new" htmlEscape="true"/>'>Add Jugador</a>
	</sec:authorize>
	
</idus_martii:layout>