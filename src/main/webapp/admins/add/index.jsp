<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
boolean auth = AuthController.checkSuperAuth(request, response);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Registrar administrador</title>
	<link href="../../styles/general.css" rel="stylesheet">
	<link href="../../styles/forms.css" rel="stylesheet">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2 id="tituloHeader">Añadir administrador</h2>
	</header>
	<main class="container-md">
		<% 
			if(session.getAttribute("msg") != null){
				String[] message = (String[])session.getAttribute("msg");
		%>
				<div class="mt-2 col-12 alert <%= message[0] == "ok" ? "alert-success" : "alert-danger" %>"><%= message[1] %></div>
		<% 
				session.removeAttribute("msg");
			}
		%>
		<form method="post" action="../../AuthController?action=register" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<div class="mb-3">
			    <label for="username" class="form-label">Nombre</label>
			    <input type="text" class="form-control" placeholder="Nombre de usuario" name="username" id="username" required>
		   	</div>
		   	<div class="mb-3">
			    <label for="password" class="form-label">Contraseña</label>
			    <input type="password" class="form-control" placeholder="Contraseña de acceso" name="password" id="password" required>
		   	</div>
		   	<div class="mb-3">
			    <label for="superadmin" class="form-label">Superadministrador</label>
			    <select class="form-select" name="superadmin" id="superadmin">
			    	<option value="n">No</option>
			    	<option value="y">Sí</option>
			    </select>
		   	</div>
			  
			<button type="submit" class="btn btn-primary">Registrar</button>
		</form>
	</main>
</body>
</html>