<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Category" %>
<%@ page import="juanlucas.models.Article" %>
<%@ page import="juanlucas.models.Provider" %>
<%@ page import="juanlucas.controllers.CategoriesController" %>
<%@ page import="juanlucas.controllers.ProvidersController" %>
<%@ page import="juanlucas.controllers.ArticlesController" %>
<% 
	/*@ include file = "..jsp"*/
	boolean redirect = true;
	Article article = null;
	if(request.getParameter("id")!=null && request.getParameter("id") != "" ){
		article = ArticlesController.find(request.getParameter("id"));
		if(article != null){
			redirect = false;
		}
	}
	
	if(redirect){
		String[] error = new String[2];
		error[0] = "ko";
		error[1] = "Artículo no encontrado.";
		session.setAttribute("msg", error );
		response.setStatus(302);
		response.setHeader("Location", "..");
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Editar artículo</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2>Editar artículo</h2>
	</header>
	<main class="container-md">
		<% 
			if(session.getAttribute("msg") != null){
				String[] message = (String[])session.getAttribute("msg");
		%>
				<div class="mt-2 alert <%= message[0] == "ok" ? "alert-success" : "alert-danger" %>"><%= message[1] %></div>
		<% 
				session.removeAttribute("msg");
			}
		%>
		<form method="post" action="../../ArticlesController?action=update" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<input type="hidden" value="<%= article.getId() %>" name="id">
			<div class="mb-3">
			    <label for="description" class="form-label">Descripción</label>
			    <input type="text" class="form-control" name="description" id="description" value="<%= article.getDescription() %>" required>
			  </div>
			  <div class="mb-3">
			    <label for="price" class="form-label">Precio</label>
			    <input type="number" step="0.01" min="0" class="form-control" name="price" value="<%= article.getPrice() %>" id="price" required>
			  </div>
			  <div class="mb-3">
			    <label for="category" class="form-label">Categoría</label>
			    <select class="form-select" id="category" name="category">
			    	<option value=""></option>
			    	<% 
			    		ArrayList<Category> list = CategoriesController.list();
			    		for(int i = 0; i<list.size();i++){
							Category category = list.get(i);
			    	%>
			    		<option value="<%= category.getId() %>" <%= article.getCategory_id()==category.getId() ? "selected" : "" %>><%= category.getTitle() %></option>
			    	<% } %>
			    </select>
			  </div>
			  <div class="mb-3">
			    <label for="provider" class="form-label">Proveedor</label>
			    <select class="form-select" id="provider" name="provider">
			    	<option value=""></option>
			    	<% 
			    		ArrayList<Provider> providers = ProvidersController.list();
			    		for(int i = 0; i<providers.size();i++){
							Provider provider = providers.get(i);
			    	%>
			    		<option value="<%= provider.getId() %>" <%= article.getProvider_id()==provider.getId() ? "selected" : "" %>><%= provider.getName() %></option>
			    	<% } %>
			    </select>
			  </div>
			  <button type="submit" class="btn btn-success">Actualizar</button>
		</form>
	</main>
</body>
</html>