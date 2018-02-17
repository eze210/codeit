<%@ page import="codeit.Vigencia" %>
<g:if test="${!vigencia.estaVigente()}">
    <div>Cerrado</div>
</g:if>
<g:else>
    <g:if test="${vigencia.tipo == codeit.Vigencia.Tipo.Infinita}">
        <div>Abierto</div>
    </g:if>
    <g:else>
        <div>Abierto hasta <g:formatDate date="${vigencia.rangoDeFechas.upperEndpoint().toDate()}" type="date" style="SHORT"/></div>
    </g:else>
</g:else>