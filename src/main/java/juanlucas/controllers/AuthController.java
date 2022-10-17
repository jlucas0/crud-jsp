package juanlucas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import juanlucas.models.Admin;

import java.io.IOException;

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
		switch(action) {
			case "login":
				result = doLogin(request);
				destination = ".";
				returnUrl = "login";
				if(result[0] == "ok") {
					destination = ".";
				}
				break;
			case "logout":
				request.getSession().removeAttribute("loged");
				result[0] = "ok";
				destination = "/ProyectoCRUD/login/";
				break;
			
			case "delete":
				//result = doRemove(request);
				break;
			default:
				result[1] = "Acción no encontrada";
		}
		
		session = request.getSession();
		
		if(result[0] == "ko") {
			destination += "/"+returnUrl;
		}
		if(!action.equals("login")&&!action.equals("logout")) {
			session.setAttribute("msg", result );
		}
		response.setStatus(302);
		response.setHeader("Location", destination);
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
			
			if(admin.getPassword().equals(request.getParameter("password"))) {
				request.getSession().setAttribute("loged", admin.getUsername());
				result[0] = "ok";
			}else {
				result[1] = "Credenciales incorrectas.";
			}
		}else {
			result[1] = "Usuario no encontrado.";
		}
		
		return result;
	}
	
	
	public static void checkAuth(HttpServletRequest request, HttpServletResponse response) {
		if(request.getSession().getAttribute("loged") == null) {
			response.setStatus(302);
			response.setHeader("Location", "/ProyectoCRUD/login/");
		}
	}	
}
