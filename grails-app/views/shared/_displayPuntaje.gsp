<h2>Facetas</h2>
<g:each var="faceta" in="${puntaje.facetas}">
    <div class="card_wrapper" style="display: inline-block;"><div class="card">
        <p>${"${faceta.tipo}"}: ${faceta.puntosAcumulados}</p>
    </div></div>
</g:each>
<h2>Insignias conseguidas</h2>
<g:if test="${puntaje.insignias.size() > 0}">
    <g:each var="insignia" in="${puntaje.insignias}">
        <div class="card_wrapper" style="display: inline-block;"><div class="card">
            <strong>${insignia}</strong><br/>
        </div></div>
    </g:each>
</g:if>
<g:else>
    <p>No tiene ninguna insignia aún</p>
</g:else>
