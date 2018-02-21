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
                    <label>Título</label> <label class="wrong_color">*</label>
                    <g:textField name="titulo"/>
                    <label>Descripción</label> <label class="wrong_color">*</label>
                    <g:textArea name="descripcion"/>
                    %{--TODO: Add vigencia--}%
                    %{--TODO: Add exercises--}%
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="Crear" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
