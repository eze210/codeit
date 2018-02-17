<%@ page import="codeit.Vigencia" %>
<g:if test="${!vigencia.estaVigente()}">
    <p>Cerrado</p>
</g:if>
<g:else>
    <g:if test="${vigencia.tipo == codeit.Vigencia.Tipo.Infinita}">
        <p>Abierto</p>
    </g:if>
    <g:else>
        <p>Abierto hasta <g:formatDate date="${vigencia.rangoDeFechas.upperEndpoint().toDate()}" type="date" style="SHORT"/></p>
    </g:else>
</g:else>