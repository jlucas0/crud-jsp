package juanlucas.models;

import java.util.ArrayList;

import juanlucas.dao.AdminDAO;

public class Admin {
	
	private int id;
	private String username;
	private String password;
	
	private static AdminDAO DAO;
	
	public Admin() {
		DAO = new AdminDAO();
	}
	
	public Admin(int id,String username) {
		this.id = id;
		this.setUsername(username);
		DAO = new AdminDAO();
	}
	
	public Admin(int id,String username,String password) {
		this.id = id;
		this.setUsername(username);
		this.setPassword(password);
		DAO = new AdminDAO();
	}
	
	
	public static ArrayList<Admin> list(){
		DAO =  new AdminDAO();	
		return DAO.get();
		
	}
	
	public static Admin find(int id) {
		DAO = new AdminDAO();
		Admin result = DAO.getById(id);
		return result;
	}
	
	public static Admin findByName(String username) {
		DAO = new AdminDAO();
		Admin result = DAO.getByUsername(username);
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

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}
