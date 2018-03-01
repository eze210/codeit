<div>
    <g:if test="${style == 'short'}">
        <div class="circle ${resultado.valido == null ? "processing_background" : (resultado.valido ? "valid_background" : "wrong_background")}" style="display: inline-block;" />
        <g:if test="${style == "short" && resultado.valido}">
            <label style="display: inline-block;">${resultado.puntaje}</label>
        </g:if>
    </g:if>
    <g:else>
        <span class="${resultado.valido == null ? "processing_color" : (resultado.valido ? "valid_color" : "wrong_color")}">
            ${resultado.valido == null ? "Procesando solución ..." : (resultado.valido ? "Puntaje: " + String.valueOf(resultado.puntaje) : "No es válida")}
        </span>
    </g:else>
</div>