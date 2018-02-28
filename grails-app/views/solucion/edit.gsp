<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Editar Solución a ${solucion.desafio.titulo}</title>
    </head>
    <body>
        <g:loggedInProgramador>
        <div class="breadcrumble">
            <g:link controller="desafio" action="index">Desafíos</g:link>
            > <g:link controller="desafio" action="show" id="${solucion.desafio.id}">${solucion.desafio.titulo}</g:link>
            > Editar <g:link action="show" id="${solucion.id}">mi solución</g:link>
        </div>
        <div id="edit-solucion" class="content scaffold-edit" role="main">
            <h1>Editar mi Solución a ${solucion.desafio.titulo}</h1>
            <p>Participando como ${solucion.desafio.obtenerSolucionDe(programador).participante.nombre}</p>

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

            <g:form name="atributos_solucion" controller="solucion" action="update" method="PUT">
                <fieldset class="form">
                    <label>Descripción</label> <label class="wrong_color">*</label> <br/>
                %{--TODO: Add login and use participante from there--}%
                    <g:hiddenField name="solucion_id" value="${solucion.id}" />
                    <g:textArea name="descripcion" value="${params.descripcion != null ? params.descripcion : solucion.descripcion}"/>
                    <g:each var="ejercicio" status="i" in="${solucion.desafio.ejercicios.toSorted()}">
                        <tmpl:/shared/ejercicioDisplay ejercicio="${ejercicio}" numero="${i+1}" />
                        <label>Resolución</label> (ponga aquí su código) <br/>
                        <g:set var="resolucion" value="${solucion.resoluciones.find { it.ejercicio.id == ejercicio.id }}" />
                        <g:textArea name="codigo" value="${params.codigo != null ? (params.list("codigo") as List)[i] : resolucion?.codigo}"/>
                        <g:hiddenField name="ejercicio_id" value="${ejercicio.id}" />
                        <g:hiddenField name="resolucion_id" value="${resolucion?.id}" />
                    </g:each>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="edit" class="update" value="Editar" />
                </fieldset>
            </g:form>
        </div>
        </g:loggedInProgramador>
    </body>
</html>
