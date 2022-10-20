package juanlucas.models;

import java.util.ArrayList;

import juanlucas.dao.ProviderDAO;

public class Provider {
	private int id;
	private String name;
	private String address;
	private String phone;
	private int articles;
	
	private static ProviderDAO DAO;
	
	public Provider() {}
	
	public Provider(int id, String name) {
		this.id=id;
		this.name=name;
	}
	
	public Provider(int id, String name, int articles) {
		this.id=id;
		this.name=name;
		this.articles = articles;
	}
	
	public static ArrayList<Provider> list(){
		DAO =  new ProviderDAO();	
		return DAO.get();
		
	}
	
	public static Provider find(int id) {
		DAO = new ProviderDAO();
		Provider result = DAO.getById(id);
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getArticles() {
		return articles;
	}

	public void setArticles(int articles) {
		this.articles = articles;
	}
}
