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
	private static final long serialVersionUID = 2L;
	private String index = "articles";
	private String add = "add";
	private HttpSession session;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*RequestDispatcher vista = request.getRequestDispatcher(index);
		vista.forward(request,response);*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String result[] = new String[3];
		switch(action) {
			case "register":
				result = doRegister(request);
				break;
			default:
				
		}
		
		session = request.getSession();

		String destination = index;
		
		if(result[0] == "ko") {
			destination = result[2];
		}

		session.setAttribute("msg", result );
		response.setStatus(302);
		response.setHeader("Location", destination);
	}

	private String[] doRegister(HttpServletRequest request) throws ServletException, IOException {
		String description = request.getParameter("description");
		String result[] = new String[3];
		result[0] = "ko";
		result[2] = index+add;
		if(description != null && description != "") {
			if(request.getParameter("price")!= null && request.getParameter("price") != "") {
				float price = Float.parseFloat(request.getParameter("price"));
				if(price >= 0) {
					Article article = new Article();
					article.setDescription(description);
					article.setPrice(price);
					if(request.getParameter("category")!=null&&request.getParameter("category")!="") {
						article.setCategory_id(Integer.parseInt(request.getParameter("category")));
					}
					if(article.save()) {
						result[0] = "ok";
						result[1] = "Artículo registrado correctamente";
					}else {
						result[1] = "Error al guardar el artículo.";
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
