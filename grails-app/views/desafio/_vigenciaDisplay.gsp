<%@ page import="org.joda.time.DateTime; codeit.Vigencia" %>
<g:if test="${!vigencia.estaVigente()}">
    <p>Cerrado</p>
    <g:if test="${vigencia.posteriorA(new org.joda.time.DateTime())}">
        <p>Abrirá en <g:formatDate date="${vigencia.rangoDeFechas.lowerEndpoint().toDate()}" type="date" style="SHORT"/></p>
    </g:if>
</g:if>
<g:else>
    <g:if test="${vigencia.rangoDeFechas.hasUpperBound()}">
        <p>Abierto hasta <g:formatDate date="${vigencia.rangoDeFechas.upperEndpoint().toDate()}" type="date" style="SHORT"/></p>
    </g:if>
    <g:else>
        <p>Abierto</p>
    </g:else>
</g:else>