<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Login</title>
	<link href="../styles/general.css" rel="stylesheet">
	<link href="../styles/forms.css" rel="stylesheet">
</head>
<body>
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
		<form method="post" action="../AuthController?action=login" class="mt-5 col-12 col-sm-8 offset-sm-2 col-lg-6 offset-lg-3">
			<div class="mb-3">
			    <label for="user" class="form-label">Usuario</label>
			    <input type="text" class="form-control" name="user" id="user" required>
			  </div>
			  <div class="mb-3">
			    <label for="password" class="form-label">Contraseña</label>
			    <input type="password" class="form-control" name="password" id="password" required>
			  </div>
			  <button type="submit" class="btn btn-primary">Acceder</button>
		</form>
	</main>
</body>
</html>