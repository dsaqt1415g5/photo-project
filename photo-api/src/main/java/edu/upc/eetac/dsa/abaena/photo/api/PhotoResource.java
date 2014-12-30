package edu.upc.eetac.dsa.abaena.photo.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.abaena.photo.api.DataSourceSPA;
import edu.upc.eetac.dsa.abaena.photo.api.model.Coment;
import edu.upc.eetac.dsa.abaena.photo.api.model.ComentCollection;
import edu.upc.eetac.dsa.abaena.photo.api.model.Photo;
import edu.upc.eetac.dsa.abaena.photo.api.model.PhotoCollection;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;




@Path("/photos")
public class PhotoResource {
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	private String GET_COMMENTS_BY_IDPHOTO="Select * from Comments where idphoto = ?";
	private String INSERT_COMMENT="insert into Comments (username, idphoto, content) values (?,?,?)";
	private String DELETE_COMMENT_QUERY ="delete from comments where idcomment = ?";
	private String GET_COMMENT_BY_ID="Select * from comments where idcomment=?";
	private String UPDATE_COMMENT_QUERY="update comments set content=ifnull(?,content) where idcomment = ?";
	
	@Context
	private Application app;
	private SecurityContext security;
	
	@GET
	@Produces(MediaType2.PHOTO_API_COMENT_COLLECTION)
	public ComentCollection getCommentCollectionByIdPhoto(@QueryParam("idphoto") String idphoto){
		
		ComentCollection comments = new ComentCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(GET_COMMENTS_BY_IDPHOTO);
			stmt.setString(1, idphoto);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Coment comment = new Coment();
				comment.setIdcomment(rs.getInt("idcomment"));
				comment.setUsername(rs.getString("username"));
				comment.setIdphoto(rs.getString("idphoto"));
				comment.setCreationTimestamp(rs.getTimestamp("creationTimestamp").getTime());
				comment.setContent(rs.getString("content"));	
				comments.addComment(comment);
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
		return comments;
	}
	
	@POST
	@Consumes(MediaType2.PHOTO_API_COMENT)
	@Produces(MediaType2.PHOTO_API_COMENT)
	public Coment createComent(Coment comment){

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_COMMENT,Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, comment.getUsername());
			stmt.setString(2, comment.getIdphoto());
			stmt.setString(3, comment.getContent());
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
		
		return comment;
	}
	
	@DELETE
	public void deleteComment (@QueryParam("idcomment") int idcomment){

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_COMMENT_QUERY);
			stmt.setInt(1, Integer.valueOf(idcomment));
	 
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
	
	@GET
	@Produces(MediaType2.PHOTO_API_COMENT)
	public Coment getComment(@QueryParam("idcomment") int idcomment){
		
		Coment comment =new Coment();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_COMMENT_BY_ID);
			stmt.setInt(1, Integer.valueOf(idcomment));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				comment.setIdcomment(rs.getInt("idcomment"));
				comment.setUsername(rs.getString("username"));
				comment.setIdphoto(rs.getString("idphoto"));
				comment.setCreationTimestamp(rs.getTimestamp("creationTimestamp").getTime());
				comment.setContent(rs.getString("content"));
			}else {
				throw new NotFoundException("There's no comment with idcomment="
						+ idcomment);
					}

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
	 
		return comment;
		
	}
	
public Coment getCommentFromDataBase(int idcomment){
		
		Coment comment =new Coment();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_COMMENT_BY_ID);
			stmt.setInt(1, Integer.valueOf(idcomment));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				comment.setIdcomment(rs.getInt("idcomment"));
				comment.setUsername(rs.getString("username"));
				comment.setIdphoto(rs.getString("idphoto"));
				comment.setCreationTimestamp(rs.getTimestamp("creationTimestamp").getTime());
				comment.setContent(rs.getString("content"));
			}else {
				throw new NotFoundException("There's no comment with idcomment="
						+ idcomment);
					}

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
	 
		return comment;
		
	}
	
	
	@PUT
	@Consumes(MediaType2.PHOTO_API_COMENT)
	@Produces(MediaType2.PHOTO_API_COMENT)
	public Coment updateComment (@QueryParam("idcomment") int idcomment, Coment comment){

		//validateUser(idcomment);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_COMMENT_QUERY);
			stmt.setString(1, comment.getContent());
			stmt.setInt(2, idcomment);
			int rows = stmt.executeUpdate();
			if (rows == 1)
				comment = getComment(idcomment);
			else {
				;// Updating inexistent sting
			}
	 
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
	 
		return comment;
		
		
	}
	
	private void validateUser(int id) {
	    Coment comment = getCommentFromDataBase(id);
	    String username = comment.getUsername();
		if (!security.getUserPrincipal().getName().equals(username))
			throw new ForbiddenException(
					"You are not allowed to modify or delete this comment.");
	}
	
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Photo uploadImage(@FormDataParam("idphoto") String idphoto,
			@FormDataParam("username") String username,
			@FormDataParam("autor") String autor,
			@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("image") InputStream image,
			@FormDataParam("image") FormDataContentDisposition fileDisposition) {
		UUID uuid = writeAndConvertImage(image);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("insert into Photos (idphoto, "
					+ "username, autor, name, description) "
					+ "values(?,?,?,?,?)");
			
			stmt.setString(1, uuid.toString());
			stmt.setString(2, username);
			stmt.setString(3, autor);
			stmt.setString(4, name);
			stmt.setString(5, description);
			stmt.executeUpdate();
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
		Photo imageData = new Photo();
		imageData.setIdphoto(uuid.toString() + ".png");


		return imageData;
	}
	
	private UUID writeAndConvertImage(InputStream file) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(file);

		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Something has been wrong when reading the file.");
		}
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString() + ".png";
		try {
			ImageIO.write(image,"png",new File(app.getProperties().get("uploadFolder") + filename));
			
		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Something has been wrong when converting the file.");
		}

		return uuid;
	}
	
	

	
}
