<%@ page import="codeit.Programador" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Editar ${desafio.titulo}</title>
    </head>
    <body>
        <div class="breadcrumble">
            %{--TODO: Use logged user--}%
            <g:link controller="desafio" action="index" params="[from: codeit.Programador.findById(5).id]">Mis desafíos</g:link>
            > <g:link controller="desafio" action="show" id="${desafio.id}">${desafio.titulo}</g:link>
            > Editar</div>
        </div>
        <div id="edit-desafio" class="content scaffold-edit" role="main">
            <h1>Editar ${desafio.titulo}</h1>

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

            <g:form name="atributos_desafio" controller="desafio" action="update" method="PUT">
                <fieldset class="form">
            %{--TODO: Add login and use participante from there--}%
                    <g:hiddenField name="desafio_id" value="${desafio.id}" />
                    <label for="titulo">
                        Título:
                        <span class="required-indicator">*</span>
                    </label>
                    <g:textField name="titulo" value="${params?.titulo ?: desafio.titulo}"/>

                    <label for="descripcion">
                        Descripción:
                        <span class="required-indicator">*</span>
                    </label>
                    <g:textArea name="descripcion" value="${params?.descripcion ?: desafio.descripcion}"/>
                    <br/>
                %{--TODO: Add vigencia--}%

                    <label>
                        Ejercicios:
                        <span class="required-indicator">*</span>
                    </label>
                    <div class="padded_container">
                        <g:if test="${params.enunciados}">
                            <g:each var="enunciado" status="i" in="${params.enunciados}">
                                <label>
                                    ${i}. Enunciado:
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:textArea name="enunciado" value="${enunciado}" />
                                <div class="padded_container">
                                    <div class="padded_container">
                                        <g:each var="prueba" status="j" in="${[params["entrada_${i}"]] + [params["salida_esperada_${i}"]].transpose()}">
                                            <label>
                                                ${j + 1}. Prueba
                                                <span class="required-indicator">*</span>
                                            </label>
                                            <br/>
                                            <label>Entrada: </label>
                                            <g:textField name="entrada_${i}" value="${prueba[0]}" />
                                            <label>Salida esperada: </label>
                                            <g:textField name="salida_esperada_${i}" value="${prueba[1]}" />
                                            <br/>
                                        </g:each>
                                    </div>
                                </div>
                                <br/>
                            </g:each>
                        </g:if>
                        <g:else>
                            <g:each var="ejercicio" status="i" in="${desafio.ejercicios.toSorted()}">
                                <label>
                                    ${i + 1}. Enunciado:
                                    <span class="required-indicator">*</span>
                                </label>
                                <g:textArea name="enunciado" value="${ejercicio.enunciado}" />
                                <div class="padded_container">
                                    <div class="padded_container">
                                        <g:each var="prueba" status="j" in="${ejercicio.pruebas.toSorted()}">
                                            <label>
                                                ${j + 1}. Prueba
                                                <span class="required-indicator">*</span>
                                            </label>
                                            <br/>
                                            <label>Entrada: </label>
                                            <g:textField name="entrada_${i}" value="${prueba.entrada}" />
                                            <label>Salida esperada: </label>
                                            <g:textField name="salida_esperada_${i}" value="${prueba.salidaEsperada}" />
                                            <br/>
                                        </g:each>
                                    </div>
                                </div>
                                <br/>
                            </g:each>
                        </g:else>
                    </div>
                    <br/>
                    <div>
                        <label>
                            ¿Más ejercicios?
                        </label>
                        <dynamic:block itemId="ejercicio" min="0">
                            <label>
                                Enunciado:
                                <span class="required-indicator">*</span>
                            </label>
                            <g:textArea name="enunciado" />
                        </dynamic:block>
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="edit" class="update" value="Actualizar" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
