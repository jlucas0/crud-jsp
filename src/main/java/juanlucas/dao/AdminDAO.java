package juanlucas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import juanlucas.configs.DBConector;
import juanlucas.models.Admin;

public class AdminDAO {
	DBConector conector;
	Connection connection;
	PreparedStatement prepared;
	

	public ArrayList<Admin> get() {
		
		 ArrayList<Admin> data = new ArrayList<Admin>();
		
		 conector = new DBConector();
		 connection = conector.getConnection();
		 String sql = "SELECT admins.* FROM admins";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Admin record = new Admin(result.getInt("id"),result.getString("username"));
								
				data.add(record);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 conector.disconnect();
		 
		 return data;
	}
	
	public Admin getByUsername(String username) {
		
		Admin admin = null;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "SELECT * FROM admins WHERE username = '"+username+"'";
		try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			if(result.next()){
				
				admin = new Admin(result.getInt("id"),result.getString("username"),result.getString("password"));

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		conector.disconnect();
		 
		return admin;
	}
	

	public Admin getById(int id) {
		
		Admin admin = null;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "SELECT * FROM admins WHERE id = "+id;
		try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			if(result.next()){
				
				admin = new Admin(result.getInt("id"),result.getString("username"),result.getString("password"));

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		conector.disconnect();
		 
		return admin;
	}

	
	public boolean save(Admin admin) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "INSERT INTO admins(username,password) VALUES (";
				
		sql += "'"+admin.getUsername()+"'";
		sql += ","+admin.getPassword();
		sql += ");";
		try {
			
			prepared = connection.prepareStatement(sql);
			int updated = prepared.executeUpdate(sql, 1);
			if(updated<0) {
				result = false;
			}else {
				ResultSet keys = prepared.getGeneratedKeys();
				keys.next();
				admin.setId(keys.getInt(1));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		 
		 
		conector.disconnect();
		
		return result;
	}
	
	public boolean update(Admin admin) {
		boolean result = true;
		conector = new DBConector();
		connection = conector.getConnection();
		String sql = "UPDATE admins SET";
				
		sql += " username='"+admin.getUsername()+"'";
		sql += ", password="+admin.getPassword();
		sql += " WHERE id="+admin.getId()+";";
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
		String sql = "DELETE FROM admins WHERE id ="+id;
				
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
