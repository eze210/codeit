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
            <g:if test="${flash.errors}">
                <ul class="errors" role="alert">
                    <g:each in="${flash.errors}">
                        <li>${it}</li>
                    </g:each>
                </ul>
            </g:if>

            <g:form name="atributos_solucion" controller="solucion" action="save">
                <fieldset class="form">
                    <g:loggedInProgramador>
                        <g:set var="participantesDisponibles" value="${[programador] + programador.equipos.findAll { desafio.puedeParticipar(it) }}" />
                        <g:if test="${participantesDisponibles.size() > 1}">
                            <label>Participar como</label> <label class="wrong_color">*</label>
                            <g:select name="participante_id" from="${participantesDisponibles}" optionKey="id" optionValue="nombre" value="${programador.nombre}"/>
                        </g:if>
                        <g:else>
                            <label>Participando como ${participantesDisponibles.first().nombre}</label>
                            <g:hiddenField name="participante_id" value="${participantesDisponibles.first().id}" />
                        </g:else>
                        <br/>
                    </g:loggedInProgramador>
                    <label>Descripción</label> <label class="wrong_color">*</label> <br/>
                    <g:hiddenField name="desafio_id" value="${desafio.id}" />
                    <g:textArea name="descripcion"/>
                    <g:each var="ejercicio" status="i" in="${desafio.ejercicios.toSorted()}">
                        <tmpl:/shared/ejercicioDisplay ejercicio="${ejercicio}" numero="${i+1}" />
                        <label>Resolución</label> (ponga aquí su código) <br/>
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
