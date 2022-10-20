package juanlucas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import juanlucas.configs.DBConector;
import juanlucas.models.Category;

public class CategoryDAO {
	DBConector conector;
	Connection connection;
	PreparedStatement prepared;
	

	public ArrayList<Category> get() {
		
		 ArrayList<Category> data = new ArrayList<Category>();
		
		 conector = new DBConector();
		 connection = conector.getConnection();
		 String sql = "SELECT *, COUNT(articles.id) AS articles FROM categories LEFT JOIN articles ON categories.id = articles.category GROUP BY categories.id";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Category record = new Category(result.getInt("id"),result.getString("title"),result.getInt("articles"));
				
				data.add(record);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 conector.disconnect();
		 
		 return data;
	}
	
	
	public Category getById(int id) {
		
		Category category = null;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "SELECT * FROM categories WHERE id = "+id;
		try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			if(result.next()){
				
				category = new Category(result.getInt("id"),result.getString("title"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		conector.disconnect();
		 
		return category;
	}

	public boolean save(Category category) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "INSERT INTO categories(title) VALUES (";
				
		sql += "'"+category.getTitle()+"'";
		sql += ");";
		try {
			
			prepared = connection.prepareStatement(sql);
			int updated = prepared.executeUpdate(sql, 1);
			if(updated<0) {
				result = false;
			}else {
				ResultSet keys = prepared.getGeneratedKeys();
				keys.next();
				category.setId(keys.getInt(1));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		 
		 
		conector.disconnect();
		
		return result;
	}
	
	public boolean update(Category category) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "UPDATE categories SET";
				
		sql += " title='"+category.getTitle()+"'";
		sql += " WHERE id="+category.getId()+";";
		try {
			
			prepared = connection.prepareStatement(sql);
			int updated = prepared.executeUpdate(sql);
			if(updated<0) {
				result = false;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		 
		 
		conector.disconnect();
		
		return result;
	}

	public boolean remove(int id) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "DELETE FROM categories WHERE id ="+id;
				
		try {
			
			prepared = connection.prepareStatement(sql);
			int updated = prepared.executeUpdate(sql);
			if(updated<0) {
				result = false;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		 
		 
		conector.disconnect();
		
		return result;
	}

}
