<g:if test="${equiposCount > 0}">
    <div>
        <tmpl:participanteListTemplate list="${equiposList}" name="Equipos" />
    </div>
    <div class="paginate">
        <util:remotePaginate controller="participante" action="listadoEquipos" total="${equiposCount}" update="equipos" />
    </div>
</g:if>