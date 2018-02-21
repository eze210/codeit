<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${desafio ? "Soluciones para " + desafio.titulo : "Mis soluciones"}</title>
    </head>
    <body>
        <g:if test="${desafio != null}">
            <div class="breadcrumble"><g:link controller="desafio" action="index">Desafios</g:link>
                > <g:link controller="desafio" action="show" id="${desafio.id}">${desafio.titulo}</g:link>
                > Soluciones
            </div>
        </g:if>
        <div class="nav" role="navigation">
            <ul>
                <g:if test="${desafio != null}">
                %{--TODO: Deshabilitar si ya tiene su solución--}%
                    <li><g:link class="create" action="create" params="[to: desafio.id]">Proponer una solución</g:link></li>
                </g:if>
            </ul>
        </div>

        <div id="list-solucion" class="content scaffold-list" role="main">
            <h1>${desafio ? "Soluciones para " + desafio.titulo : "Mis soluciones"}</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <table>
                <thead>
                </thead>
                <tbody>
                <g:each in="${solucionList}" var="solucion">
                    <tr>
                        <div class="card_wrapper">
                            <div class="solucion card">
                                <h2 style="overflow: hidden;">
                                    <div style="display: inline-block;">
                                        <g:link method="GET" resource="${solucion.participante}">${solucion.participante.nombre}</g:link>
                                    </div>
                                    <div style="display: inline-block;">
                                        <tmpl:/shared/estadoSolucionDisplay resultado="${solucion.resultado}" style="${'short'}" />
                                    </div>
                                </h2>
                                <p> <em>${solucion.descripcion}</em> </p>
                                <p><g:link method="GET" resource="${solucion}">Ver más</g:link></p>
                            </div>
                        </div>
                    </tr>
                </g:each>
                </tbody>
            </table>

            <div class="pagination">
                <g:paginate total="${solucionCount ?: 0}" />
            </div>
        </div>
    </body>
</html>