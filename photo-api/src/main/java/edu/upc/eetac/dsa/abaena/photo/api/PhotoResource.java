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

@Path("/photos")
public class PhotoResource {
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	private String GET_COMMENTS_BY_IDPHOTO="Select * from Comments where idphoto = ?";
	private String INSERT_COMMENT="insert into Comments (username, idphoto, content) values (?,?,?)";
	private String DELETE_COMMENT_QUERY ="delete from comments where idcomment = ?";
	private String GET_COMMENT_BY_ID="Select * from comments where idcomment=?";
	private String UPDATE_COMMENT_QUERY="update comments set content=ifnull(?,content) where idcomment = ?";
	@Context
	private SecurityContext security;
	
	@GET
	@Produces(MediaType.PHOTO_API_COMENT_COLLECTION)
	public ComentCollection getCommentCollectionByIdPhoto(@QueryParam("idphoto") int idphoto){
		
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
			stmt.setInt(1, idphoto);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Coment comment = new Coment();
				comment.setIdcomment(rs.getInt("idcomment"));
				comment.setUsername(rs.getString("username"));
				comment.setIdphoto(rs.getInt("idphoto"));
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
	@Consumes(MediaType.PHOTO_API_COMENT)
	@Produces(MediaType.PHOTO_API_COMENT)
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
			stmt.setInt(2, comment.getIdphoto());
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
	@Produces(MediaType.PHOTO_API_COMENT)
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
				comment.setIdphoto(rs.getInt("idphoto"));
				comment.setCreationTimestamp(rs.getTimestamp("creationTimestamp").getTime());
				comment.setContent(rs.getString("content"));
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
	@Consumes(MediaType.PHOTO_API_COMENT)
	@Produces(MediaType.PHOTO_API_COMENT)
	public Coment updateComment (@QueryParam("idcomment") int idcomment, Coment comment){
		
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
	
}
