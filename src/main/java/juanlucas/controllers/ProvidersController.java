package juanlucas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import juanlucas.models.Provider;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class ProvidersController
 */
public class ProvidersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String index = "providers";
	private HttpSession session;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String result[] = new String[2];
		String returnUrl = "";
		switch(action) {
			case "register":
				result = doSave(request);
				returnUrl = "add";
				break;
			case "update":
				result = doSave(request);
				returnUrl = "edit?id="+request.getParameter("id");
				break;
			case "delete":
				result = doRemove(request);
				break;
			default:
				result[1] = "Acción no encontrada";				
		}
		
		session = request.getSession();

		String destination = index;
		
		if(result[0] == "ko") {
			destination += "/"+returnUrl;
		}

		session.setAttribute("msg", result );
		response.setStatus(302);
		response.setHeader("Location", destination);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	private String[] doSave(HttpServletRequest request) throws ServletException, IOException {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String result[] = new String[2];
		result[0] = "ko";
		if(name != null && name != "") {
			Provider provider = new Provider();
			if(request.getParameter("id")!=null) {
				int id = Integer.parseInt(request.getParameter("id"));
				provider = Provider.find(id);
			}
			provider.setName(name);
			provider.setAddress(address);
			provider.setPhone(phone);
			if(provider.save()) {
				result[0] = "ok";
				result[1] = "Proveedor registrado correctamente";
			}else {
				result[1] = "Error al guardar el proveedor";
			}
					
		}else {
			result[1] = "No se ha indicado el nombre";
		}
				
		return result;
	}
	

	private String[] doRemove(HttpServletRequest request) throws ServletException, IOException {
		
		String result[] = new String[2];
		result[0] = "ko";
		
		if(request.getParameter("id")!= null && request.getParameter("id") != "") {
			try {	
				int id = Integer.parseInt(request.getParameter("id"));
			
				Provider provider = Provider.find(id);
				if(provider!=null) {
					if(provider.delete()) {
						result[0] = "ok";
						result[1] = "Proveedor eliminado correctamente";
					}else {
						result[1] = "Error al eliminar el proveedor";
					}
				} else {
					result[1] = "Proveedor no encontrado";
				}
				
				
			}catch(NumberFormatException e) {
				result[1] = "ID incorrecto";
			}
		}else {
			result[1] = "ID incorrecto";
		}
		
		return result;
	}
	
	public static ArrayList<Provider> list(){
		return Provider.list();
	}
	
	public static Provider find(String parameter) {
		
		Provider provider = null;
		
		try {
			int id = Integer.parseInt(parameter);
			provider = Provider.find(id);
		}catch(NumberFormatException ex){
			
		}
		
		return provider;
	}
	
}
