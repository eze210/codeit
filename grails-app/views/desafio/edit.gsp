<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Editar ${desafio.titulo}</title>
    </head>
    <body>
        <div class="breadcrumble">

        </div>
        <div id="edit-desafio" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.desafio}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.desafio}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.desafio}" method="PUT">
                <g:hiddenField name="version" value="${this.desafio?.version}" />
                <fieldset class="form">
                    <f:all bean="desafio"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
