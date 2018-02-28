<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${programador.nombre}</title>
    </head>
    <body>
        <div class="breadcrumble"><g:link controller="participante" action="index">Participantes</g:link> > ${programador.nombre}</div>
        <div class="nav" role="navigation">
            <ul>
                %{--TODO--}%
                %{--<g:link class="edit" action="edit" resource="${programador}">Editar</g:link>--}%
            </ul>
        </div>
        <div id="show-programador" class="content scaffold-show" role="main">
            <h1>${programador.nombre}</h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div class="padded_container">
                <tmpl:/shared/displayPuntaje puntaje="${programador.puntaje}" /><br/>
                <h2>Equipos</h2>
                <g:if test="${programador.equipos.size() > 0}">
                    <g:each var="equipo" in="${programador.equipos}">
                        <div class="card_wrapper">
                            <div class="card">
                                <g:link method="GET" resource="${equipo}" >${equipo.nombre}</g:link>
                            </div>
                        </div>
                    </g:each>
                </g:if>
                <g:else>
                    <p>No tiene equpos aún</p>
                </g:else>

                <h2>Desafíos propuestos</h2>
                <g:if test="${programador.desafiosCreados.size() > 0}">
                    <g:render template="/shared/displayDesafio" collection="${programador.desafiosCreados}" />
                </g:if>
                <g:else>
                    <p>No ha propuesto ningún desafío aún</p>
                </g:else>

                <h2>Desafíos de los que participa</h2>
                <g:if test="${programador.desafios.size() > 0}">
                    <g:render template="/shared/displayDesafio" collection="${programador.desafios}" />
                </g:if>
                <g:else>
                    <p>No ha participado de ningún desafío aún</p>
                </g:else>
            </div>
        </div>
    </body>
</html>
