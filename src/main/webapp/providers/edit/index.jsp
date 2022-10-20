<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Provider" %>
<%@ page import="juanlucas.controllers.ProvidersController" %>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
	AuthController.checkAuth(request, response);
%>
<% 
	/*@ include file = "..jsp"*/
	boolean redirect = true;
	Provider provider = null;
	if(request.getParameter("id")!=null && request.getParameter("id") != "" ){
		provider = ProvidersController.find(request.getParameter("id"));
		if(provider != null){
			redirect = false;
		}
	}
	
	if(redirect){
		String[] error = new String[2];
		error[0] = "ko";
		error[1] = "Proveedor no encontrado";
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
	<title>Editar proveedor</title>
	<link href="../../styles/general.css" rel="stylesheet">
	<link href="../../styles/forms.css" rel="stylesheet">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2 id="tituloHeader">Editar proveedor</h2>
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
		<form method="post" action="../../ProvidersController?action=update" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<input type="hidden" value="<%= provider.getId() %>" name="id">
			<div class="mb-3">
			    <label for="name" class="form-label">Nombre</label>
			    <input type="text" class="form-control" name="name" id="name" value="<%= provider.getName() %>" required>
		  	</div>
		  	<div class="mb-3">
			    <label for="phone" class="form-label">Teléfono</label>
			    <input type="text" class="form-control" name="phone" id="phone" value="<%= provider.getPhone() %>">
		  	</div>
		  	<div class="mb-3">
			    <label for="address" class="form-label">Dirección</label>
			    <input type="text" class="form-control" name="address" id="address" value="<%= provider.getAddress() %>">
		  	</div>
	    	<button type="submit" class="btn btn-success">Actualizar</button>
		</form>
	</main>
</body>
</html>
<% } %>