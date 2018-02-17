<g:if test="${equiposCount > 0}">
    <div>
        <tmpl:participanteListTemplate list="${equiposList}" name="Equipos" />
    </div>
    <div class="paginate">
        <util:remotePaginate controller="participante" action="listadoEquipos" total="${equiposCount}" update="equipos" />
    </div>
</g:if>
<g:else>
    <div>No hay equipos por el momento. ¡Sé el primero con tus amigos!</div>
</g:else>