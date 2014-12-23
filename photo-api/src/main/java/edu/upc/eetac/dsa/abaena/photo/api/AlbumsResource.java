package edu.upc.eetac.dsa.abaena.photo.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.abaena.photo.api.model.Albums;
import edu.upc.eetac.dsa.abaena.photo.api.model.AlbumsCollection;

@Path("/albums")
public class AlbumsResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String GET_ALBUMS_BY_USERNAME="select * from albums where username=?";
	@Context
	private SecurityContext security;
	
	@GET
	@Consumes(MediaType.PHOTO_API_ALBUM_COLLECTION)
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
}
