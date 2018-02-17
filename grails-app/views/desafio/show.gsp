<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="Desafio" />
        <title>${desafio.titulo}</title>
    </head>
    <body>
        <div class="breadcrumble"><g:link action="index">Desafios</g:link> > ${desafio.titulo}</div>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-desafio" class="content scaffold-show" role="main">
            <h1>${desafio.titulo}</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div style="padding: 10px 10px;">
                <tmpl:vigenciaDisplay vigencia="${desafio.vigencia}" />
                <p><em>${desafio.descripcion}</em></p>

                <h2>Ejercicios</h2>
                <g:each var="ejercicio" status="i" in="${desafio.ejercicios}">
                    <tmpl:ejercicioDisplay ejercicio="${ejercicio}" numero="${i+1}"/>
                </g:each>
            </div>

            <%-- TODO: CUANDO AGREGUEMOS EDICIÓN PARA QUIEN SEA EL CREADOR
            <g:form resource="${this.desafio}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.desafio}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
            --%>
        </div>
    </body>
</html>
