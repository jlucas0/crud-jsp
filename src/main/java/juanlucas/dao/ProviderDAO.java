package juanlucas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import juanlucas.configs.DBConector;
import juanlucas.models.Provider;

public class ProviderDAO {
	DBConector conector;
	Connection connection;
	PreparedStatement prepared;
	

	public ArrayList<Provider> get() {
		
		 ArrayList<Provider> data = new ArrayList<Provider>();
		
		 conector = new DBConector();
		 connection = conector.getConnection();
		 String sql = "SELECT *, COUNT(articles.id) AS articles FROM providers LEFT JOIN articles ON providers.id = articles.provider GROUP BY providers.id";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Provider record = new Provider(result.getInt("id"),result.getString("name"));
				
				
				record.setAddress(result.getString("address"));
				record.setPhone(result.getString("phone"));
				record.setArticles(result.getInt("articles"));
				
				data.add(record);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 conector.disconnect();
		 
		 return data;
	}
	

	public Provider getById(int id) {
		
		Provider provider = null;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "SELECT * FROM providers WHERE id = "+id;
		try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			if(result.next()){
				
				provider = new Provider(result.getInt("id"),result.getString("name"));
				provider.setAddress(result.getString("address"));
				provider.setPhone(result.getString("phone"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		conector.disconnect();
		 
		return provider;
	}

	public boolean save(Provider provider) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "INSERT INTO providers(name,address,phone) VALUES (";
				
		sql += "'"+provider.getName()+"'";
		sql += ",'"+provider.getAddress()+"'";
		sql += ",'"+provider.getPhone()+"'";
		sql += ");";
		try {
			
			prepared = connection.prepareStatement(sql);
			int updated = prepared.executeUpdate(sql, 1);
			if(updated<0) {
				result = false;
			}else {
				ResultSet keys = prepared.getGeneratedKeys();
				keys.next();
				provider.setId(keys.getInt(1));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		 
		 
		conector.disconnect();
		
		return result;
	}
	
	public boolean update(Provider provider) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "UPDATE providers SET";
				
		sql += " name='"+provider.getName()+"'";
		sql += " ,phone='"+provider.getPhone()+"'";
		sql += " ,address='"+provider.getAddress()+"'";
		sql += " WHERE id="+provider.getId()+";";
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
		String sql = "DELETE FROM providers WHERE id ="+id;
				
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
