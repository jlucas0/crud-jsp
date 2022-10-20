package juanlucas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import juanlucas.models.Article;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class ArticlesController
 */
public class ArticlesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String index = "articles";
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
				returnUrl = "edit";
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
		String description = request.getParameter("description");
		String result[] = new String[2];
		result[0] = "ko";
		if(description != null && description != "") {
			if(request.getParameter("price")!= null && request.getParameter("price") != "") {
				float price = Float.parseFloat(request.getParameter("price"));
				if(price >= 0) {
					Article article = new Article();
					if(request.getParameter("id")!=null) {
						int id = Integer.parseInt(request.getParameter("id"));
						article = Article.find(id);
					}
					article.setDescription(description);
					article.setPrice(price);
					if(request.getParameter("category")!=null&&request.getParameter("category")!="") {
						article.setCategory_id(Integer.parseInt(request.getParameter("category")));
					}else {
						article.setCategory_id(-1);
					}
					if(request.getParameter("provider")!=null&&request.getParameter("provider")!="") {
						article.setProvider_id(Integer.parseInt(request.getParameter("provider")));
					}else {
						article.setProvider_id(-1);
					}
					if(article.save()) {
						result[0] = "ok";
						result[1] = "Artículo registrado correctamente";
					}else {
						result[1] = "Error al guardar el artículo";
					}
					
				}else {
					result[1] = "Precio negativo";
				}
			}else {
				result[1] = "No hay precio";
			}
		}
		else {
			result[1] = "No hay descripción";
		}
		
		return result;
	}
	

	private String[] doRemove(HttpServletRequest request) throws ServletException, IOException {
		
		String result[] = new String[2];
		result[0] = "ko";
		
		if(request.getParameter("id")!= null && request.getParameter("id") != "") {
			try {	
				int id = Integer.parseInt(request.getParameter("id"));
			
				Article article = Article.find(id);
				if(article!=null) {
					if(article.delete()) {
						result[0] = "ok";
						result[1] = "Artículo eliminado correctamente";
					}else {
						result[1] = "Error al eliminar el artículo";
					}
				} else {
					result[1] = "Artículo no encontrado";
				}
				
				
			}catch(NumberFormatException e) {
				result[1] = "ID incorrecto";
			}
		}else {
			result[1] = "ID incorrecto";
		}
		
		return result;
	}
	
	public static ArrayList<Article> list(){
		return Article.list();
	}
	
	public static Article find(String parameter) {
		
		Article article = null;
		
		try {
			int id = Integer.parseInt(parameter);
			article = Article.find(id);
		}catch(NumberFormatException ex){
			
		}
		
		return article;
	}
}
