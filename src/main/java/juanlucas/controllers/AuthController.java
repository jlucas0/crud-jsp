package juanlucas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import juanlucas.models.Admin;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Servlet implementation class AuthController
 */
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String index = "admins";
	private HttpSession session;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String result[] = new String[2];
		String destination = index;
		String returnUrl = "";
		boolean autorizado = true;
		switch(action) {
			case "login":
				result = doLogin(request);
				destination = ".";
				returnUrl = "login";
				break;
			case "logout":
				request.getSession().removeAttribute("loged");
				result[0] = "ok";
				destination = "/ProyectoCRUD/login/";
				break;
			
			default:
				if(checkSuperAuth(request, response)) {
					switch(action) {
						case "register":
							result = doSave(request);
							returnUrl = "add";
							break;
						case "update":
							System.out.println("update");
							result = doSave(request);
							System.out.println(result[0]);
							returnUrl = "edit?id="+request.getParameter("id");
							break;
						case "delete":
							result = doRemove(request);
							break;
						default:
							result[1] = "Acción no encontrada";
					}
				}else {
					autorizado = false;
				}
		}
		
		if(autorizado) {
			session = request.getSession();
			
			if(result[0] == "ko") {
				destination += "/"+returnUrl;
			}
			if((!action.equals("login")&&!action.equals("logout")) || (action.equals("login")&&destination.equals("./login"))) {
				session.setAttribute("msg", result );
			}
			response.setStatus(302);
			response.setHeader("Location", destination);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String[] doLogin(HttpServletRequest request) {
		String username = request.getParameter("user");
		Admin admin = Admin.findByName(username);
		String result[] = new String[2];
		result[0] = "ko";
		if(admin != null) {
			if(admin.getPassword().equals(getSHA512(request.getParameter("password")))) {
				request.getSession().setAttribute("loged", admin);
				result[0] = "ok";
			}else {
				result[1] = "Credenciales incorrectas.";
			}
		}else {
			result[1] = "Usuario no encontrado.";
		}
		
		return result;
	}
	
	
	public static boolean checkAuth(HttpServletRequest request, HttpServletResponse response) {
		if(request.getSession().getAttribute("loged") == null) {
			response.setStatus(302);
			response.setHeader("Location", "/ProyectoCRUD/login/");
			return false;
		}
		return true;
	}
	
	public static boolean checkSuperAuth(HttpServletRequest request, HttpServletResponse response) {
		Admin loged = (Admin)request.getSession().getAttribute("loged");
		if(loged == null) {
			response.setStatus(302);
			response.setHeader("Location", "/ProyectoCRUD/login/");
			return false;
		}else if(!loged.isSuperadmin()) {
			response.setStatus(403);
			return false;
		}
		return true;
	}

	private String getSHA512(String input){

		String toReturn = null;
		try {
		    MessageDigest digest = MessageDigest.getInstance("SHA-512");
		    digest.reset();
		    digest.update(input.getBytes("utf8"));
		    toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return toReturn;
	}
	
	private String[] doSave(HttpServletRequest request) throws ServletException, IOException {
		String username = request.getParameter("username");
		String result[] = new String[2];
		result[0] = "ko";
		if(username != null && username != "") {
			
			Admin admin = new Admin();
			if(request.getParameter("id")!=null) {
				int id = Integer.parseInt(request.getParameter("id"));
				admin = Admin.find(id);
			}
			admin.setUsername(username);
			String password = request.getParameter("password");
			if(password != null && password != "") {
				admin.setPassword(getSHA512(password));
			}
			String superadmin = request.getParameter("superadmin");
			if(superadmin.equals("y")) {
				admin.setSuperadmin(true);
			}else {
				admin.setSuperadmin(false);
			}
			
			if(admin.save()) {
				result[0] = "ok";
				result[1] = "Administrador registrado correctamente";
			}else {
				result[1] = "Error al guardar el administrador";
			}
					
		}else {
			result[1] = "No se ha indicado el nombre de usuario";
		}
		
		return result;
	}
	

	private String[] doRemove(HttpServletRequest request) throws ServletException, IOException {
		
		String result[] = new String[2];
		result[0] = "ko";
		
		if(request.getParameter("id")!= null && request.getParameter("id") != "") {
			try {	
				int id = Integer.parseInt(request.getParameter("id"));
			
				Admin admin = Admin.find(id);
				if(admin!=null) {
					if(admin.delete()) {
						result[0] = "ok";
						result[1] = "Administrador eliminado correctamente";
					}else {
						result[1] = "Error al eliminar el administrador";
					}
				} else {
					result[1] = "Administrador no encontrado";
				}
				
				
			}catch(NumberFormatException e) {
				result[1] = "ID incorrecto";
			}
		}else {
			result[1] = "ID incorrecto";
		}
		
		return result;
	}
	
	public static ArrayList<Admin> list(){
		return Admin.list();
	}
	
	public static Admin find(String parameter) {
		
		Admin admin = null;
		
		try {
			int id = Integer.parseInt(parameter);
			admin = Admin.find(id);
		}catch(NumberFormatException ex){
			
		}
		
		return admin;
	}
}
