<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${participante ? "Desafíos de " + participante.nombre : "Desafíos"}</title>
    </head>
    <body>
        <g:if test="${participante}">
            <div class="breadcrumble">
                <g:link controller="participante" action="index">Participantes</g:link>
                > <g:link controller="participante" action="show" id="${participante.id}">${participante.nombre}</g:link>
                > Desafíos</div>
        </g:if>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="Desafio" /></g:link></li>
            </ul>
        </div>
        <div id="list-desafio" class="content scaffold-list" role="main">
            <h1>Desafíos</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <tmpl:desafioList collection="${desafioList}" />

            <div class="pagination">
                <g:paginate total="${desafioCount ?: 0}" />
            </div>
        </div>
    </body>
</html>