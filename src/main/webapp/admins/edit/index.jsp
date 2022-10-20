<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="juanlucas.models.Admin" %>
<%@ page import="juanlucas.controllers.AuthController" %>
<%
	boolean auth = AuthController.checkSuperAuth(request, response);
%>
<% 
	/*@ include file = "..jsp"*/
	boolean redirect = true;
	Admin admin = null;
	if(request.getParameter("id")!=null && request.getParameter("id") != "" ){
		admin = AuthController.find(request.getParameter("id"));
		if(admin != null){
			redirect = false;
		}
	}
	
	if(redirect){
		String[] error = new String[2];
		error[0] = "ko";
		error[1] = "Administrador no encontrado";
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
	<title>Editar administrador</title>
	<link href="../../styles/general.css" rel="stylesheet">
	<link href="../../styles/forms.css" rel="stylesheet">
</head>
<body>
	<header class="border-bottom d-flex justify-content-between p-3">
		<a href="../" class="btn btn-primary">Volver</a>
		<h2 id="tituloHeader">Editar administrador</h2>
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
		<form method="post" action="../../AuthController?action=update" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<input type="hidden" value="<%= admin.getId() %>" name="id">
			<div class="mb-3">
			    <label for="username" class="form-label">Nombre</label>
			    <input type="text" class="form-control" name="username" id="username" value="<%= admin.getUsername() %>" required>
		  	</div>
		  	<div class="mb-3">
			    <label for="password" class="form-label">Contraseña</label>
			    <input type="password" class="form-control" name="password" id="password" placeholder="Nueva contraseña">
		  	</div>
		  	<div class="mb-3">
			    <label for="superadmin" class="form-label">Superadministrador</label>
			    <select class="form-select" id="superadmin" name="superadmin">
			    		<option value="n">No</option>
			    		<option value="y" <%= (admin.isSuperadmin() ? "selected" : "") %>>Sí</option>
			    </select>
		  	</div>
	    	<button type="submit" class="btn btn-success">Actualizar</button>
		</form>
	</main>
</body>
</html>
<% } %>