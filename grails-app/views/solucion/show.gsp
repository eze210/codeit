<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Solución para ${solucion.desafio.titulo} - Solución de ${solucion.participante.nombre}</title>
    </head>
    <body>
        <div class="breadcrumble">
            <g:link controller="desafio" action="index">Desafios</g:link>
            > <g:link controller="desafio" action="show" id="${solucion.desafio.id}">${solucion.desafio.titulo}</g:link>
            > Solución de <g:link controller="participante" action="show" id="${solucion.desafio.creador.id}">${solucion.participante.nombre}</g:link></div>
        <div class="nav" role="navigation">
            %{--TODO: Habilitar o deshabilitar depende de si puede o no crear--}%
            <ul>
                <li><g:link class="list" action="index">Ver mis soluciones</g:link></li>
                <li><g:link class="create" action="create">Crear mi propia solución al desafío</g:link></li>
                <li><g:link class="edit" action="edit" id="${solucion.id}">Editar</g:link></li>
            </ul>
        </div>

        <div id="show-solucion" class="content scaffold-show" role="main">
            <h1>${solucion.desafio.titulo}</h1>
            <div class="padded_container">
                <em>por <g:link controller="participante" action="show" id="${solucion.participante.id}">${solucion.participante.nombre}</g:link></em>
                <br/><br/>
                <tmpl:/shared/estadoSolucionDisplay resultado="${solucion.resultado}" style="${'long'}" />
                <br/><br/>
                <p>${solucion.descripcion}</p>
            </div>

            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div class="padded_container">
                <g:each var="ejercicio" status="i" in="${solucion.desafio.ejercicios.toSorted()}">
                    <div class="card_container">
                        <tmpl:/shared/ejercicioDisplay ejercicio="${ejercicio}" numero="${i+1}" />
                        <div class="card_wrapper">
                            <div class="card soft-card">
                                <label>${solucion.resoluciones.find { it.ejercicio.id == ejercicio.id }?.codigo ?: ""}</label>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
        </div>
    </body>
</html>
