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
		 String sql = "SELECT articles.*, categories.title FROM articles LEFT JOIN categories ON articles.category = categories.id";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Article record = new Article(result.getInt("id"),result.getString("description"),result.getFloat("price"));
				
				if(result.getInt("category") > 0) {
					record.setCategory_id(result.getInt("category"));
					record.associateCategory(result.getInt("category"), result.getString("title"));
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
					article.associateCategory(result.getInt("category"), result.getString("title"));
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
		sql += ",NULL);";
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
}
