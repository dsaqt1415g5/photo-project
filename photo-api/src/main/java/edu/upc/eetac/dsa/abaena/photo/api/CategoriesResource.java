package edu.upc.eetac.dsa.abaena.photo.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.abaena.photo.api.model.Categories;
import edu.upc.eetac.dsa.abaena.photo.api.model.CategoriesCollection;





@Path("/categories")
public class CategoriesResource {
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	@Context
	private SecurityContext security;
	
	private String GET_CATEGORIES = "Select * from Categories";
	
	@GET
	@Produces(MediaType.PHOTO_API_CATEGORIES_COLLECTION)
	public CategoriesCollection getCategoriesCollection(String nombre){
		
		CategoriesCollection categories = new CategoriesCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(GET_CATEGORIES);
			//stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Categories category = new Categories();
				category.setIdcategory(rs.getInt("idcategory"));
				category.setNombre(rs.getString("nombre"));
				categories.addCategories(category);
			}
			
		}catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
		}
	}
	return categories;
	}

}
