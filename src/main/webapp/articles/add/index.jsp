<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Category" %>
<%@ page import="juanlucas.controllers.CategoriesController" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registrar artículo</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2>Añadir artículo</h2>
	</header>
	<main class="container-md">
		<form method="post" action="../../ArticlesController?action=register" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<div class="mb-3">
			    <label for="description" class="form-label">Descripción</label>
			    <input type="text" class="form-control" name="description" id="description" required>
			  </div>
			  <div class="mb-3">
			    <label for="price" class="form-label">Precio</label>
			    <input type="number" step="0.01" min="0" class="form-control" name="price" id="price" required>
			  </div>
			   <div class="mb-3">
			    <label for="category" class="form-label">Categoría</label>
			    <select class="form-select" id="category" name="category">
			    	<option></option>
			    	<% 
			    		ArrayList<Category> list = CategoriesController.list();
			    		for(int i = 0; i<list.size();i++){
							Category category = list.get(i);
			    	%>
			    		<option value="<%= category.getId() %>"><%= category.getTitle() %></option>
			    	<% } %>
			    </select>
			  </div>
			  <button type="submit" class="btn btn-primary">Registrar</button>
		</form>
	</main>
</body>
</html>