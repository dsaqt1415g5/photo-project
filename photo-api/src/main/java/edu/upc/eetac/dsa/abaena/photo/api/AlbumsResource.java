package edu.upc.eetac.dsa.abaena.photo.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.abaena.photo.api.model.Albums;
import edu.upc.eetac.dsa.abaena.photo.api.model.AlbumsCollection;
import edu.upc.eetac.dsa.abaena.photo.api.model.User;


@Path("/albums")
public class AlbumsResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String GET_ALBUMS_BY_USERNAME="Select * from Albums where username=?";
	private String INSERT_ALBUM="Insert into Albums (nombre, description, username) values (?,?,?)";
	private String DELETE_ALBUM_QUERY="Delete from Albums where idalbum = ?";
	private String EDITAR_ALBUM = "Update Albums set nombre=ifnull(?,nombre), description=ifnull(?,description) where idalbum=?";
	private String GET_ALBUM_BY_ID = "Select * from Albums where idalbum=?";
	
	@Context
	private SecurityContext security;
	
	@GET
	@Consumes(MediaType2.PHOTO_API_ALBUM_COLLECTION)
	public AlbumsCollection getAlbumsCollection(@QueryParam("username") String username){
		
		AlbumsCollection albums = new AlbumsCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(GET_ALBUMS_BY_USERNAME);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Albums album = new Albums();
				album.setIdalbum(rs.getInt("idalbum"));
				album.setNombre(rs.getString("nombre"));
				album.setDescription(rs.getString("description"));
				album.setUsername(rs.getString("username"));
				albums.addAlbum(album);
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

		return albums;
	}
	
	@POST
	@Consumes(MediaType2.PHOTO_API_ALBUM)
	@Produces(MediaType2.PHOTO_API_ALBUM)
	public Albums createAlbum (Albums album){
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_ALBUM,Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, album.getNombre());
			stmt.setString(2, album.getDescription());
			stmt.setString(3, album.getUsername());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			//if (rs.next()) {
				//int idcomment = rs.getInt(1);
	 
				//comment = getComment(Integer.toString(idcomment));
			//} else {
				// Something has failed...
			//}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return album;
	}
	
	@DELETE
	public void deleteAlbum (@QueryParam("idalbum") int idalbum){

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_ALBUM_QUERY);
			stmt.setInt(1, idalbum);
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				;// Deleting inexistent sting
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
	}
	
	
	@PUT
	@Consumes(MediaType2.PHOTO_API_ALBUM)
	@Produces(MediaType2.PHOTO_API_ALBUM)
	public Albums updateAlbum (@QueryParam("idalbum") String idalbum, Albums album){
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {
					
			stmt = conn.prepareStatement(EDITAR_ALBUM);
		
			stmt.setString(1, album.getNombre());
			stmt.setString(2, album.getDescription());
			stmt.setInt(3,Integer.valueOf( idalbum));
	 
			int rows = stmt.executeUpdate();
			
			if (rows == 1)
				
				album = getAlbumFromDataBase(idalbum);
			else {
				throw new NotFoundException("No existe ningún álbum con este id = "
						+ idalbum);
			}
	 
		} catch (SQLException e) {
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
		return album;
		
		
	}
	
	private Albums getAlbumFromDataBase (String idalbum){
		
		Albums album = new Albums();
		 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);

		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_ALBUM_BY_ID);
			stmt.setInt(1, Integer.valueOf(idalbum));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				album.setIdalbum(rs.getInt("idalbum"));
				album.setUsername(rs.getString("username"));
				album.setNombre(rs.getString("nombre"));
				album.setDescription(rs.getString("description"));
			
			}
			
		
		else {
			throw new NotFoundException("No existe ningún álbum con este ID = "
			+ idalbum);
			}
	} catch (SQLException e) {
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
 
	return album;
		}
	
	}
	

