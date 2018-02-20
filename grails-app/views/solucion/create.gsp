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

            <g:if test="${errors}">
            <ul class="errors" role="alert">
                <g:each var="error" in="${errors}">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:each>
            </ul>
            </g:if>

            <g:form name="atributos_solucion" controller="solucion" action="save">
                <fieldset class="form">
                    <label>Descripción</label> * <br/>
                    %{--TODO: Add login and use participante from there--}%
                    <g:hiddenField name="participante_id" value="5" />
                    <g:hiddenField name="desafio_id" value="${desafio.id}" />
                    <g:textArea name="descripcion"/>
                    <g:each var="ejercicio" status="i" in="${desafio.ejercicios.toSorted()}">
                        <g:render template="/shared/ejercicioDisplay" model="[ejercicio: ejercicio, numero: i+1]" />
                        <label>Resolución</label> (Ponga aquí su código) <br/>
                        <g:textArea name="codigo"/>
                        <g:hiddenField name="ejercicio_id" value="${ejercicio.id}" />
                    </g:each>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="Crear" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
