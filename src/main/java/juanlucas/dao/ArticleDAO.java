package juanlucas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import juanlucas.configs.DBConector;
import juanlucas.models.Article;

public class ArticleDAO {
	DBConector conector;
	Connection connection;
	PreparedStatement prepared;
	

	public ArrayList<Article> get() {
		
		 ArrayList<Article> data = new ArrayList<Article>();
		
		 conector = new DBConector();
		 connection = conector.getConnection();
		 String sql = "SELECT articles.*, categories.title, providers.name FROM articles LEFT JOIN categories ON articles.category = categories.id LEFT JOIN providers ON articles.provider = providers.id";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Article record = new Article(result.getInt("id"),result.getString("description"),result.getFloat("price"));
				
				if(result.getInt("category") > 0) {
					record.setCategory_id(result.getInt("category"));
					record.associateCategory(result.getInt("category"), result.getString("title"));
				}
				if(result.getInt("provider") > 0) {
					record.setProvider_id(result.getInt("provider"));
					record.associateProvider(result.getInt("provider"), result.getString("name"));
				}
				
				data.add(record);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 conector.disconnect();
		 
		 return data;
	}
	
	public Article getById(int id) {
		
		Article article = null;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "SELECT * FROM articles WHERE id = "+id;
		try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			if(result.next()){
				
				article = new Article(result.getInt("id"),result.getString("description"),result.getFloat("price"));
				
				if(result.getInt("category") > 0) {
					article.setCategory_id(result.getInt("category"));
				}
				
				if(result.getInt("provider") > 0) {
					article.setProvider_id(result.getInt("provider"));
				}

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		conector.disconnect();
		 
		return article;
	}

	public boolean save(Article article) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "INSERT INTO articles(description,price,category,provider) VALUES (";
				
		sql += "'"+article.getDescription()+"'";
		sql += ","+article.getPrice();
		sql += "," + (article.getCategory_id() > 0 ? article.getCategory_id() : "NULL");
		sql += "," + (article.getProvider_id() > 0 ? article.getProvider_id() : "NULL");
		sql += ");";
		try {
			
			prepared = connection.prepareStatement(sql);
			int updated = prepared.executeUpdate(sql, 1);
			if(updated<0) {
				result = false;
			}else {
				ResultSet keys = prepared.getGeneratedKeys();
				keys.next();
				article.setId(keys.getInt(1));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		 
		 
		conector.disconnect();
		
		return result;
	}
	
	public boolean update(Article article) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "UPDATE articles SET";
				
		sql += " description='"+article.getDescription()+"'";
		sql += ", price="+article.getPrice();
		sql += ", category=" + (article.getCategory_id() > 0 ? article.getCategory_id() : "NULL");
		sql += ", provider=" + (article.getProvider_id() > 0 ? article.getProvider_id() : "NULL");
		sql += " WHERE id="+article.getId()+";";
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
