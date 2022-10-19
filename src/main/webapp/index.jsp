<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
	AuthController.checkAuth(request, response);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Gestor de Artículos</title>
	<link href="styles/general.css" rel="stylesheet">
</head>
<body>
	<main class="container pt-5">
		<h1 class="text-center col-12">Gestiona el catálogo</h1>
		<h3 class="text-center col-12">Selecciona una opción</h3>
		<div class="row mt-2">
			<a href="articles" class="btn btn-primary col-8 offset-2 col-md-6 offset-md-3 col-lg-4 offset-lg-4">Artículos</a>
		</div>
		<div class="row mt-2">
			<a href="" class="btn btn-primary col-8 offset-2 col-md-6 offset-md-3 col-lg-4 offset-lg-4">Categorías</a>
		</div>
		<div class="row mt-2">
			<a href="" class="btn btn-primary col-8 offset-2 col-md-6 offset-md-3 col-lg-4 offset-lg-4">Proveedores</a>
		</div>
	</main>
</body>
</html>