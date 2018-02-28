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
                <g:loggedInProgramador>
                    <g:if test="${programador.id == desafio.creador.id}">
                        <li><g:link class="edit" action="edit" resource="${desafio}">Editar</g:link></li>
                    </g:if>
                </g:loggedInProgramador>
            </ul>
        </div>
        <div id="show-desafio" class="content scaffold-show" role="main">
            <h1>${desafio.titulo}</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div class="padded_container">
                <tmpl:vigenciaDisplay vigencia="${desafio.vigencia}" />
                <br/>
                <p><em>${desafio.descripcion}</em></p>

                <h2>Ejercicios</h2>
                <g:each var="ejercicio" status="i" in="${desafio.ejercicios.toSorted()}">
                    <tmpl:/shared/ejercicioDisplay ejercicio="${ejercicio}" numero="${i+1}" />
                </g:each>

                <h2>Soluciones</h2>
                <p>
                    <g:link controller="solucion" action="index" params="[to: desafio.id]"><g:quantityOf number="${desafio.resultados.size()}" word="solución" plural="soluciones" /></g:link> por ahora,
                    <g:quantityOf number="${desafio.resultados.findAll { it.correcto }.size()}" word="correcta" />,
                    <g:quantityOf number="${desafio.resultados.findAll { it.valido }.size()}" word="valida" />

                </p>
                <div class="nav" role="navigation">
                    <ul>
                        <g:loggedInProgramador>
                            <g:if test="${desafio.puedeParticipar(programador)}">
                                <g:set var="solucion" value="${desafio.obtenerSolucionDe(programador)}" />
                                <g:if test="${solucion}">
                                    <li><g:link class="edit" action="edit" controller="solucion" id="solucion.id">Editar Solución</g:link></li>
                                </g:if>
                                <g:else>
                                    <li><g:link class="create" action="create" controller="solucion" params="[to: desafio.id]">Proponer Solución</g:link></li>
                                </g:else>
                            </g:if>
                        </g:loggedInProgramador>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
