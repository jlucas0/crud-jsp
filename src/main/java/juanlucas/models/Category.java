package juanlucas.models;

import java.util.ArrayList;

import juanlucas.dao.CategoryDAO;

public class Category {
	private int id;
	private String title;
	
	private static CategoryDAO DAO;
	
	public Category(int id, String title) {
		this.id=id;
		this.title=title;
	}
	
	public static ArrayList<Category> list(){
		DAO =  new CategoryDAO();	
		return DAO.get();
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
