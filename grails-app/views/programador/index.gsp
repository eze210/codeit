<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Nuevo Programador Form</title>
	</head>
	<body>
        <g:form name="Registro de Programador" controller="programador" action="guardarNuevoProgramador">
            <label>Nombre: </label>
            <g:textField name="nombre"/>
            <br/>
            <g:submitButton name="Crear"/>
        </g:form>
	</body>
</html>