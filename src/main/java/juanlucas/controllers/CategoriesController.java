package juanlucas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import juanlucas.models.Category;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class CategoriesController
 */
public class CategoriesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String index = "categories";
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
		String title = request.getParameter("title");
		String result[] = new String[2];
		result[0] = "ko";
		if(title != null && title != "") {
			
			Category category = new Category();
			if(request.getParameter("id")!=null) {
				int id = Integer.parseInt(request.getParameter("id"));
				category = Category.find(id);
			}
			category.setTitle(title);
			if(category.save()) {
				result[0] = "ok";
				result[1] = "Categoría registrada correctamente";
			}else {
				result[1] = "Error al guardar la categoría";
			}
					
		}else {
			result[1] = "No se ha indicado el título";
		}
				
		return result;
	}
	

	private String[] doRemove(HttpServletRequest request) throws ServletException, IOException {
		
		String result[] = new String[2];
		result[0] = "ko";
		
		if(request.getParameter("id")!= null && request.getParameter("id") != "") {
			try {	
				int id = Integer.parseInt(request.getParameter("id"));
			
				Category category = Category.find(id);
				if(category!=null) {
					if(category.delete()) {
						result[0] = "ok";
						result[1] = "Categoría eliminada correctamente";
					}else {
						result[1] = "Error al eliminar la categoría";
					}
				} else {
					result[1] = "Categoría no encontrada";
				}
				
				
			}catch(NumberFormatException e) {
				result[1] = "ID incorrecto";
			}
		}else {
			result[1] = "ID incorrecto";
		}
		
		return result;
	}
	
	public static ArrayList<Category> list(){
		return Category.list();
	}
	
	public static Category find(String parameter) {
		
		Category category = null;
		
		try {
			int id = Integer.parseInt(parameter);
			category = Category.find(id);
		}catch(NumberFormatException ex){
			
		}
		
		return category;
	}
	
}
