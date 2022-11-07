<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="error">

    <spring:url value="/resources/images/votacion_negativa.jpg" var="errorImage"/>
    <div align="center"> <img align="center" src="${errorImage}"/>
     <h2>Something happened...</h2></div>
    <p>${exception.message}</p>
</idus_martii:layout>
