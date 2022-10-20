<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Category" %>
<%@ page import="juanlucas.controllers.CategoriesController" %>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
boolean auth = AuthController.checkAuth(request, response);
%>
<% 
	/*@ include file = "..jsp"*/
	boolean redirect = true;
	Category category = null;
	if(request.getParameter("id")!=null && request.getParameter("id") != "" ){
		category = CategoriesController.find(request.getParameter("id"));
		if(category != null){
			redirect = false;
		}
	}
	
	if(redirect){
		String[] error = new String[2];
		error[0] = "ko";
		error[1] = "Categoría no encontrada";
		session.setAttribute("msg", error );
		response.setStatus(302);
		response.setHeader("Location", "..");
	}else{
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Editar categoría</title>
	<link href="../../styles/general.css" rel="stylesheet">
	<link href="../../styles/forms.css" rel="stylesheet">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2 id="tituloHeader">Editar categoría</h2>
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
		<form method="post" action="../../CategoriesController?action=update" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<input type="hidden" value="<%= category.getId() %>" name="id">
			<div class="mb-3">
			    <label for="title" class="form-label">Título</label>
			    <input type="text" class="form-control" name="title" id="title" value="<%= category.getTitle() %>" required>
			  </div>
			  <button type="submit" class="btn btn-success">Actualizar</button>
		</form>
	</main>
</body>
</html>
<% } %>