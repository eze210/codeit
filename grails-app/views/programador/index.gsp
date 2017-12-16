<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Nuevo Programador Form</title>
	</head>
	<body>
        <g:form name="Registro de Programador" controller="programador" action="guardarNuevoProgramador" params="nombre:nombre">
            <label>Nombre: </label>
            <g:textField name="nombre"/><br/>
            <g:actionSubmit value="Crear"/>
        </g:form>
	</body>
</html>