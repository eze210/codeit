<%@ page import="codeit.Participante" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Proponer solución</title>
    </head>
    <body>
        <div class="breadcrumble">
            <g:link controller="desafio" action="index">Desafios</g:link>
            > <g:link controller="desafio" action="show" id="${desafio.id}">${desafio.titulo}</g:link>
            > Proponer solución</div>
        <div id="create-solucion" class="content scaffold-create" role="main">
            <h1>Proponer solución</h1>

            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:hasErrors bean="${this.solucion}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.solucion}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>

            <g:form name="atributos_solucion" controller="solucion" action="save">
                <fieldset class="form">
                    <label>Descripción</label> <br/>
                    <g:textArea name="descripcion"/>
                    <g:hiddenField name="participante_id" value="5" />
                    <g:hiddenField name="desafio_id" value="${desafio.id}" />
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="Crear" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
