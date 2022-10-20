<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Provider" %>
<%@ page import="juanlucas.controllers.ProvidersController" %>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
	AuthController.checkAuth(request, response);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Proveedores</title>
	<link href="../styles/general.css" rel="stylesheet">
	<link href="../styles/lists.css" rel="stylesheet">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<h2 id="tituloHeader">Proveedores</h2>
		<div id="botonBurger">
			<div></div>
			<div></div>
			<div></div>
		</div>
		<nav id="navMovil">
			<a href="../articles">Artículos</a>
			<a href="../categories">Categorías</a>
			<a href="">Administradores</a>
			<span><%= request.getSession().getAttribute("loged") %> - <a href="../AuthController?action=logout">Salir</a></span>
		</nav>		
	</header>
	<main class="container mt-5">
		<div class="col-3 offset-9 col-sm-2 offset-sm-10 col-lg-1 offset-lg-11">
			<a href="add" class="btn btn-success">Añadir</a>
		</div>
		<% 
			if(session.getAttribute("msg") != null){
				String[] message = (String[])session.getAttribute("msg");
		%>
				<div class="mt-2 col-12 alert <%= message[0] == "ok" ? "alert-success" : "alert-danger" %>"><%= message[1] %></div>
		<% 
				session.removeAttribute("msg");
			}
		%>
		<div class="table-responsive col-12">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Código</th>
						<th>Nombre</th>
						<th>Teléfono</th>
						<th>Dirección</th>
						<th>Artículos</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				<% 
					ArrayList<Provider> list = ProvidersController.list();
					for(int i = 0; i<list.size();i++){
						Provider provider = list.get(i);
				%>
					<tr>
						<td><%= provider.getId() %></td>
						<td><%= provider.getName() %></td>
						<td><% if (provider.getPhone() != null) %><%= provider.getPhone() %></td>
						<td><% if (provider.getAddress() != null) %><%= provider.getAddress() %></td>
						<td><%= provider.getArticles() %></td>
						<td class="text-center">
							<a href="edit?id=<%= provider.getId() %>" class="btn btn-secondary">Editar</a>
							<button class="btn btn-danger deleteBtn" onclick="deleteProvider(<%= provider.getId() %>)">Borrar</button>
						</td>
					</tr>
				<%						
					}
				%>
				</tbody>
			</table>
		</div>
	</main>
	<script type="text/javascript" src="../scripts/menu.js"></script>
	<script type="text/javascript">
	
		function deleteProvider(id){
			if(confirm("¿Realmente quieres borrar el proveedor?")){
				window.location.href = "../ProvidersController?action=delete&id="+id;
			}
		}
	
	</script>
</body>
</html>