<table>
    <thead>
    </thead>
    <tbody>
        <g:each in="${collection}" var="bean" status="i">
            <tr>
                <div class="card_wrapper">
                    <div class="participante card" id="${bean?.id}">
                        <div>Nombre: <g:link method="GET" resource="${bean}">${bean.nombre}</g:link></div>
                    </div>
                </div>
            </tr>
        </g:each>
    </tbody>
</table>