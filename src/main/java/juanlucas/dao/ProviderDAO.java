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
		 String sql = "SELECT * FROM providers";
		 try {
			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				
				Provider record = new Provider(result.getInt("id"),result.getString("name"));
				
				
				record.setAddress(result.getString("address"));
				record.setPhone(result.getString("phone"));
				
				
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
