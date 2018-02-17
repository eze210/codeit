<table>
    <thead>
    </thead>
    <tbody>
    <g:each in="${collection}" var="bean" status="i">
        <tr>
            <div class="card_wrapper">
            <div class="desafio card" id="${bean?.id}">
                <h2><g:link method="GET" resource="${bean}">${bean.titulo}</g:link></h2>
                <p><em>${bean.descripcion}</em></p>
                <br/>
                <p>Creador: <g:link method="GET" resource="${bean.creador}">${bean.creador.nombre}</g:link></p>
                <tmpl:vigenciaDisplay vigencia="${bean.vigencia}"/>
            </div>
            </div>
        </tr>
    </g:each>
    </tbody>
</table>