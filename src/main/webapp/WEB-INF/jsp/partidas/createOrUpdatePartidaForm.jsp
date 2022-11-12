<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<petclinic:layout pageName="partida">
    <h2>
        <c:if test="${partida['new']}">Nueva </c:if> Partida
    </h2>
    <form:form modelAttribute="partida" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Numero Jugadores" name="nJugadores"/>
            <petclinic:inputField label="Fecha Creacion" name="fechaCreacion"/>
            <petclinic:inputField label="Fecha Inicio" name="fechaInicio"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Crear Partida</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
