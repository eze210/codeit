<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${equipo.nombre}</title>
    </head>
    <body>
        <div class="breadcrumble"><g:link controller="participante" action="index">Participantes</g:link> > ${equipo.nombre}</div>
        <div class="nav" role="navigation">
            <ul>
                %{--TODO--}%
                %{--<g:link class="edit" action="edit" resource="${equipo}">Editar</g:link>--}%
            </ul>
        </div>
        <div id="show-equipo" class="content scaffold-show" role="main">
            <h1>${equipo.nombre}</h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div class="padded_container">
                <h2>Miembros</h2>
                <g:each var="programador" in="${equipo.programadores}">
                    <g:link method="GET" resource="${programador}" >${programador.nombre}</g:link><br/>
                </g:each>

                <h2>Desafíos de los que participa</h2>
                <g:if test="${equipo.desafios.size() > 0}">
                    <g:render template="/shared/displayDesafio" collection="${equipo.desafios}" />
                </g:if>
                <g:else>
                    <p>No ha participado de ningún desafío aún</p>
                </g:else>
            </div>
        </div>
    </body>
</html>
