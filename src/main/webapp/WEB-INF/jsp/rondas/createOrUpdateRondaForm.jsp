<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="idus_martii" tagdir="/WEB-INF/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<idus_martii:layout pageName="rondas">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${ronda['new']}">Nueva </c:if> ronda
        </h2>
        <form:form modelAttribute="ronda"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${ronda.id}"/>
            <div class="form-group has-feedback">                
                <idus_martii:inputField label="Id de partida" name="partidaId"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${ronda['new']}">
                            <button class="btn btn-default" type="submit">Añadir ronda</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar ronda</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>        
    </jsp:body>
</idus_martii:layout>