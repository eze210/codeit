<h2>Facetas</h2>
<g:each var="faceta" in="${puntaje.facetas}">
    <div class="card_wrapper"><div class="card">
        <p>${"${faceta.tipo}"}: ${faceta.puntosAcumulados}</p>
    </div></div>
</g:each>
<h2>Insignias conseguidas</h2>
<g:each var="insignia" in="${puntaje.insignias}">
    <div class="card_wrapper"><div class="card">
        <strong>${insignia.nombre}</strong><br/>
        <em>${insignia.origen}</em>
    </div></div>
</g:each>