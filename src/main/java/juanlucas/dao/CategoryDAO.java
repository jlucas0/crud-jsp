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
		 String sql = "SELECT * FROM categories";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Category record = new Category(result.getInt("id"),result.getString("title"));
				
				data.add(record);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 conector.disconnect();
		 
		 return data;
	}
	
}
