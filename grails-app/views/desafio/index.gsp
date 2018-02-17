<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="Desafio" />
        <title>Desafíos</title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
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