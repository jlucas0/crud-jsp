<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
	AuthController.checkAuth(request, response);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Registrar proveedor</title>
	<link href="../../styles/general.css" rel="stylesheet">
	<link href="../../styles/forms.css" rel="stylesheet">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2 id="tituloHeader">Añadir proveedor</h2>
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
		<form method="post" action="../../ProvidersController?action=register" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<div class="mb-3">
			    <label for="name" class="form-label">Nombre</label>
			    <input type="text" class="form-control" placeholder="Nombre identificador del proveedor" name="name" id="name" required>
		   	</div>
		   	<div class="mb-3">
			    <label for="phone" class="form-label">Teléfono</label>
			    <input type="text" class="form-control" placeholder="Teléfono de contacto. Opcional." name="phone" id="phone">
		   	</div>
		   	<div class="mb-3">
			    <label for="address" class="form-label">Dirección</label>
			    <input type="text" class="form-control" placeholder="Dirección comercial. Opcional." name="address" id="address">
		   	</div>
			  
			<button type="submit" class="btn btn-primary">Registrar</button>
		</form>
	</main>
</body>
</html>