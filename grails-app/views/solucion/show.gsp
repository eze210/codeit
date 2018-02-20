<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Solución para ${solucion.desafio.titulo} - Solución de ${solucion.desafio.creador.nombre}</title>
    </head>
    <body>
        <div class="breadcrumble">
            <g:link controller="desafio" action="index">Desafios</g:link>
            > <g:link controller="desafio" action="show" id="${solucion.desafio.id}">${solucion.desafio.titulo}</g:link>
            > Solución de <g:link controller="participante" action="show" id="${solucion.desafio.creador.id}">${solucion.desafio.creador.nombre}</g:link></div>
        <div class="nav" role="navigation">
            %{--TODO: Habilitar o deshabilitar depende de si puede o no crear--}%
            <ul>
                <li><g:link class="list" action="index">Ver mis soluciones</g:link></li>
                <li><g:link class="create" action="create">Crear mi propia solución al desafío</g:link></li>
            </ul>
        </div>

        <div id="show-solucion" class="content scaffold-show" role="main">
            <h1>${solucion.desafio.titulo}</h1>
            <div class="padded_container">
                <em>por <g:link controller="participante" action="show" id="${solucion.desafio.creador.id}">${solucion.desafio.creador.nombre}</g:link></em>
                <br/><br/>
                <span style="color: ${solucion.resultado.valido != null ? (solucion.resultado.valido ? "black" : "red") : "gray"};">
                    ${solucion.resultado.valido != null ? (solucion.resultado.valido ? "Puntaje: " + String.valueOf(solucion.resultado.puntaje) : "No es válida") : "Procesando solución ..."}
                </span>
                <br/><br/>
                <p>${solucion.descripcion}</p>
            </div>

            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div class="padded_container">
                <g:each var="resolucion" status="i" in="${solucion.resoluciones.toSorted()}">
                    <div class="card_container">
                        <g:render template="/shared/ejercicioDisplay" model="[ejercicio: resolucion.ejercicio, numero: i+1]" />
                        <div class="card_wrapper">
                            <div class="card soft-card">
                                <label>${resolucion.codigo}</label>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
            %{--TODO: Cuando se pueda editar--}%
            %{--<g:form resource="${this.solucion}" method="DELETE">--}%
                %{--<fieldset class="buttons">--}%
                    %{--<g:link class="edit" action="edit" resource="${this.solucion}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
                %{--</fieldset>--}%
            %{--</g:form>--}%
        </div>
    </body>
</html>
