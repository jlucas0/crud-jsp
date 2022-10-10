package juanlucas.models;

import java.util.ArrayList;

import juanlucas.dao.ArticleDAO;

public class Article {
	
	private int id;
	private String description;
	private float price;
	private int category_id;
	private Category category;
	private int provider_id;
	private Provider provider;
	
	private static ArticleDAO DAO;
	
	public Article() {}
	
	public Article(int id,String description,float price) {
		this.id = id;
		this.setDescription(description);
		this.setPrice(price);
	}
	
	
	public static ArrayList<Article> list(){
		DAO =  new ArticleDAO();	
		return DAO.get();
		
	}
	
	public static Article find(int id) {
		DAO = new ArticleDAO();
		Article result = DAO.getById(id);
		return result;
	}

	public boolean save() {
		DAO =  new ArticleDAO();
		return DAO.save(this);
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void associateCategory(int id,String title) {
		this.setCategory(new Category(id,title));
	}

	public int getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(int provider_id) {
		this.provider_id = provider_id;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	public void associateProvider(int id,String name) {
		this.setProvider(new Provider(id,name));
	}
}
