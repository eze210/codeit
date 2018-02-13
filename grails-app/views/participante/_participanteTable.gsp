<table>
    <thead>
    </thead>
    <tbody>
        <g:each in="${collection}" var="bean" status="i">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <div class="participante" id="${bean?.id}">
                    <div>Nombre: <g:link method="GET" resource="${bean}">${bean.nombre}</g:link></div>
                </div>
            </tr>
        </g:each>
    </tbody>
</table>