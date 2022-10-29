<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="turnos">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${turno['new']}">New </c:if> Pet
        </h2>
        <form:form modelAttribute="turno"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${turno.id}"/>
            <div class="form-group has-feedback">                
                <idus_martii:inputField label="Consul" name="consul"/>
                <idus_martii:inputField label="Predor" name="predor"/>
                <idus_martii:inputField label="Edil1" name="edil1"/>
                <idus_martii:inputField label="Edil2" name="edil2"/>
                <idus_martii:inputField label="Votos Traidores" name="votosTraidores"/>
                <idus_martii:inputField label="Votos Leales" name="votosLeales"/>
                <idus_martii:inputField label="Votos Neutrales" name="votosNeutrales"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${turno['new']}">
                            <button class="btn btn-default" type="submit">Add Turno</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Turno</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>        
    </jsp:body>
</idus_martii:layout>