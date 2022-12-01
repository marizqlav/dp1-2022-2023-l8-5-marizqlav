<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="jugadores">
    <h2>
        <c:if test="${jugador['new']}">New </c:if> Jugffffador
    </h2>
    <form:form modelAttribute="jugador" class="form-horizontal" id="add-jugador-form">
        <div class="form-group has-feedback">
            <idus_martii:inputField label="Username" name="user.username"/>
            <idus_martii:inputField label="Name" name="user.name"/>
            <idus_martii:inputField label="Surname" name="user.surname"/>
            <idus_martii:inputField label="Photo" name="user.photo"/>
            <idus_martii:inputField label="Email" name="user.email"/>
            <idus_martii:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${jugador['new']}">
                        <button class="btn btn-default" type="submit">Add Jugador</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Jugador</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</idus_martii:layout>
