<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Article" %>
<%@ page import="juanlucas.controllers.ArticlesController" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Artículos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href=".." class="btn btn-primary">Volver</a>
		<h2>Artículos</h2>
	</header>
	<main class="container-sm mt-5">
		<div class="text-end">
			<a href="add" class="btn btn-success">Añadir</a>
		</div>
		<% 
			if(session.getAttribute("msg") != null){
				String[] message = (String[])session.getAttribute("msg");
		%>
				<div class="mt-2 alert <%= message[0] == "ok" ? "alert-success" : "alert-danger" %>"><%= message[1] %></div>
		<% 
				session.removeAttribute("msg");
			}
		%>
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descripción</th>
						<th>Categoría</th>
						<th>Precio</th>
						<th>Proveedor</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				<% 
					ArrayList<Article> list = ArticlesController.list();
					for(int i = 0; i<list.size();i++){
						Article article = list.get(i);
				%>
					<tr>
						<td><%= article.getId() %></td>
						<td><%= article.getDescription() %></td>
						<td>
							<% if(article.getCategory() != null) { %>
								<%= article.getCategory().getTitle() %>
							<% }else{ %>
								-
							<% } %>
						</td>
						<td><%= article.getPrice() %> €</td>
						<td>
							<% if(article.getProvider() != null) { %>
								<%= article.getProvider().getName() %>
							<% }else{ %>
								-
							<% } %>
						</td>
						<td class="text-center">
							<a href="edit?id=<%= article.getId() %>" class="btn btn-secondary">Editar</a>
							<button class="btn btn-danger deleteBtn" data-id="">Borrar</button>
						</td>
					</tr>
				<%						
					}
				%>
				</tbody>
			</table>
		</div>
	</main>
	
	<script type="text/javascript">
	
		//const myModalAlternative = new bootstrap.Modal('#myModal', options);
	
	</script>
</body>
</html>