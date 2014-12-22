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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.upc.eetac.dsa.abaena.photo.api.DataSourceSPA;
import edu.upc.eetac.dsa.abaena.photo.api.MediaType;
import edu.upc.eetac.dsa.abaena.photo.api.model.User;

@Path("/users")
public class UserResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	private final static String GET_USER_BY_USERNAME = "Select * from Users where username=?";
	private final static String INSERT_USER_INTO_USERS = "insert into Users (username, password, avatar) values(?, MD5(?), null)";
	private final static String DELETE_USER = "Delete from Users where username=? ";
	private final static String UPDATE_USER = "update Users set username=ifnull(?, username), password=ifnull(?,password) where username=?";
	
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.PHOTO_API_USER)
	public User getUser(@PathParam("username") String username) {

		User user = new User();

		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_USER_BY_USERNAME);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getInt("avatar"));

			} else {
				throw new NotFoundException(
						"No existe ningún usuario con este nombre = " + username);
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
		user.setPassword(null);
		return user;
	}

	@POST
	@Consumes(MediaType.PHOTO_API_USER)
	@Produces(MediaType.PHOTO_API_USER)
	public User CreateUser(User user) {

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmtGetUsername = null;
		PreparedStatement stmtGetPassword = null;
		PreparedStatement stmtInsertUserIntoUsers = null;

		try {
			stmtGetUsername = conn.prepareStatement(GET_USER_BY_USERNAME);
			stmtGetUsername.setString(1, user.getUsername());

			ResultSet rs = stmtGetUsername.executeQuery();
			if (rs.next())
				throw new WebApplicationException("El usuario con nombre " + user.getUsername()
						+ " ya existe.", Status.CONFLICT);// tu
																		// nombre
																		// de
																		// usuario
																		// ya
																		// existe
			rs.close();

			conn.setAutoCommit(false);// desactivamos las confirmaciones
										// automaticas
			stmtInsertUserIntoUsers = conn
					.prepareStatement(INSERT_USER_INTO_USERS);

			stmtInsertUserIntoUsers.setString(1, user.getUsername());
			stmtInsertUserIntoUsers.setString(2, user.getPassword());

			stmtInsertUserIntoUsers.executeUpdate();

			conn.commit();

		}

		catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmtGetUsername != null)
					stmtGetUsername.close();

				if (stmtInsertUserIntoUsers != null)
					stmtGetUsername.close();
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
			}
		}

		user.setPassword(null);
		return user;
	}

	@DELETE
	@Path("/{username}")
	public String deleteUser(@PathParam("username") String username) {
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_USER);
			stmt.setString(1, username);
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("No existe ningún usuario con este nombre = "
						+ username);
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
		
		return ("Usuario borrado");
	}

	
@PUT
@Path("/{username}")
@Consumes(MediaType.PHOTO_API_USER)
@Produces(MediaType.PHOTO_API_USER)
public User updateUser(@PathParam("username") String username, User user) {
	
	
	Connection conn = null;
	try {
		conn = ds.getConnection();
	} catch (SQLException e) {
		throw new ServerErrorException("Could not connect to the database",
				Response.Status.SERVICE_UNAVAILABLE);
	}
 
	PreparedStatement stmt = null;
	try {
				
		stmt = conn.prepareStatement(UPDATE_USER);
		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getPassword());
	//	stmt.setString(3, username);
 
		int rows = stmt.executeUpdate();
		
		if (rows == 1)
			
			user = getUsernameFromDatabase(username);
		
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
 
	return user;
}

private User getUsernameFromDatabase(String username) {
	
	User user = new User();

Connection conn = null;
try {
	conn = ds.getConnection();
	} catch (SQLException e) {
		throw new ServerErrorException("Could not connect to the database",
				Response.Status.SERVICE_UNAVAILABLE);	
		}

PreparedStatement stmt = null;
try {
	stmt = conn.prepareStatement(buildGetUserByUsername());
	stmt.setString(1, username);
	ResultSet rs = stmt.executeQuery();
	if (rs.next()) {
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
	

	} else {
		throw new NotFoundException("No existe ningún usuario con este nombre = "
				+ username);
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
return user;

	}

	public String buildGetUserByUsername() {
		return "SELECT *FROM Users WHERE username=?;";
	}
	
}
	

