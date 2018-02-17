<g:if test="${programadoresCount > 0}">
    <div>
        <tmpl:participanteListTemplate list="${programadoresList}" name="Programadores" />
    </div>
    <div class="paginate">
        <util:remotePaginate controller="participante" action="listadoProgramadores" total="${programadoresCount}" update="programadores" />
    </div>
</g:if>
<g:else>
    <div>No hay programadores por el momento. ¡Sé el primero!</div>
</g:else>