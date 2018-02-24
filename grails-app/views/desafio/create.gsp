<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Crear desafío</title>
    </head>
    <body>
        <div id="create-desafio" class="content scaffold-create" role="main">
            <h1>Crear desafío</h1>

            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${flash.errors}">
                <ul class="errors" role="alert">
                    <g:each in="${flash.errors}">
                        <li>${it}</li>
                    </g:each>
                </ul>
            </g:if>

            <g:form name="atributos_desafio" controller="desafio" action="save">
                <fieldset class="form">
                    %{--TODO: Add login and use participante from there--}%
                    <g:hiddenField name="creador_id" value="5" />

                    <label for="titulo">
                        Título:
                        <span class="required-indicator">*</span>
                    </label>
                    <g:textField name="titulo" value="${params?.titulo}"/>

                    <label for="descripcion">
                        Descripción:
                        <span class="required-indicator">*</span>
                    </label>
                    <g:textArea name="descripcion" value="${params?.descripcion}"/>
                    <br/>

                    <label>Válido desde: </label>
                    <g:datePicker name="desde" precision="day" noSelection="['':'-Elija si desea-']" relativeYears="[0..1]" default="none" />
                    <label>hasta:</label>
                    <g:datePicker name="hasta" precision="day" noSelection="['':'-Elija si desea-']" relativeYears="[0..1]" default="none" />
                    <br/>

                    <label>
                        Ejercicios:
                        <span class="required-indicator">*</span>
                    </label>
                    <div class="padded_container">
                        <dynamic:block itemId="ejercicio" min="1">
                            <label>
                                Enunciado:
                                <span class="required-indicator">*</span>
                            </label>
                            <g:textArea name="enunciado" />
                        </dynamic:block>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="Crear" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
