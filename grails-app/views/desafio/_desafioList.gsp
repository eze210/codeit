<html>
    <head>
        <%--<g:external dir="css" file="lists.css" />--%>
    </head>
    <body>
        <table>
            <thead>
            </thead>
            <tbody>
            <g:each in="${collection}" var="bean" status="i">
                <tr>
                    <div class="card_wrapper">
                    <div class="desafio card" id="${bean?.id}">
                        <div><g:link method="GET" resource="${bean}">${bean.titulo}</g:link></div>
                        <div>${bean.descripcion}</div>
                        <div><g:link method="GET" resource="${bean.creador}">${bean.creador.nombre}</g:link></div>
                        <tmpl:vigenciaDisplay vigencia="${bean.vigencia}"/>
                    </div>
                    </div>
                </tr>
            </g:each>
            </tbody>
        </table>
    </body>
</html>
<%--
<table>
    <thead>
    </thead>
    <tbody>
    <g:each in="${collection}" var="bean" status="i">
            <tr class="card">
                <div class="desafio" id="${bean?.id}">
                    <div><g:link method="GET" resource="${bean}">${bean.titulo}</g:link></div>
                    <div>${bean.descripcion}</div>
                    <div><g:link method="GET" resource="${bean.creador}">${bean.creador.nombre}</g:link></div>
                    <tmpl:vigenciaDisplay vigencia="${bean.vigencia}"/>
                </div>
            </tr>
    </g:each>
    </tbody>
</table>
--%>