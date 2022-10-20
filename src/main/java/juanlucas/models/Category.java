package juanlucas.models;

import java.util.ArrayList;

import juanlucas.dao.CategoryDAO;

public class Category {
	private int id;
	private String title;
	private int articles;
	
	private static CategoryDAO DAO;
	
	public Category() {};
	
	public Category(int id, String title) {
		this.id=id;
		this.title=title;
	}
	
	public Category(int id, String title,int articles) {
		this.id=id;
		this.title=title;
		this.articles = articles;
	}
	
	public static ArrayList<Category> list(){
		DAO =  new CategoryDAO();	
		return DAO.get();
		
	}
	
	public static Category find(int id) {
		DAO = new CategoryDAO();
		Category result = DAO.getById(id);
		return result;
	}
	
	public boolean save() {
		boolean result = false;
		if(this.id>0) {
			result = DAO.update(this);
		}else {
			result = DAO.save(this);
		}
		return result;
	}
	
	public boolean delete() {
		return DAO.remove(this.id);
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

	public int getArticles() {
		return articles;
	}

	public void setArticles(int articles) {
		this.articles = articles;
	}
}
