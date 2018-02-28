<table>
    <thead>
    </thead>
    <tbody>
        <g:each in="${collection}" var="participante" status="i">
            <tr>
                <div class="card_wrapper">
                    <div class="participante card" id="${participante.id}">
                        <h2><g:link method="GET" resource="${participante}">${participante.nombre}</g:link></h2>
                        <g:quantityOf number="${participante.soluciones.size()}" word="solución" plural="soluciones" /> <g:wordFromNumber number="${participante.soluciones.size()}" word="propuesta" /><br/>
                        <g:if test="${participante.hasProperty("desafiosCreados")}">
                            <g:quantityOf number="${participante.desafiosCreados.size()}" word="desafío" /> <g:wordFromNumber number="${participante.desafiosCreados.size()}" word="creado" />
                        </g:if>
                    </div>
                </div>
            </tr>
        </g:each>
    </tbody>
</table>