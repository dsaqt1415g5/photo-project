package edu.upc.eetac.dsa.abaena.photo.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
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

import org.apache.commons.codec.digest.DigestUtils;

import edu.upc.eetac.dsa.abaena.photo.api.DataSourceSPA;
import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.model.User;
import edu.upc.eetac.dsa.abaena.photo.api.model.UserCollection;

@Path("/users")
public class UserResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private final static String GET_USER_BY_USER = "select * from users where username=? and password=?";
	private final static String GET_USER_BY_USERNAME = "Select * from Users where username=?";
	private final static String INSERT_USER_INTO_USERS = "insert into Users (username, password, avatar) values(?, MD5(?), null)";
	private final static String DELETE_USER = "Delete from Users where username=? ";
	private final static String UPDATE_USER = "update Users set password=ifnull(?,password) where username=?";
	private final static String INSERT_FOLLOW = "insert into relacionuserfollows (username, followed) values (?,?)";
	private final static String GET_USERS_FOLLOWING="Select followed from relacionuserfollows where username=?";
	@GET
	@Path("/{username}")
	@Produces(MediaType2.PHOTO_API_USER)
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

	@Path("/register")
	@POST
	@Consumes(MediaType2.PHOTO_API_USER)
	@Produces(MediaType2.PHOTO_API_USER)
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
	@Consumes(MediaType2.PHOTO_API_USER)
	@Produces(MediaType2.PHOTO_API_USER)
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
	
		stmt.setString(1, user.getPassword());
		stmt.setString(2, username);
 
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

	user.setPassword(null);
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
		return "SELECT *FROM Users WHERE username=?";
	}
	
	

	@Path("/login/{username}/{password}")
	@GET
	@Produces(MediaType2.PHOTO_API_USER)
	@Consumes(MediaType2.PHOTO_API_USER)
	public User login(@PathParam("username") String username,
			@PathParam("password") String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		if (username == null || password == null)
			throw new BadRequestException(
					"El nombre de usuario y contraseña no pueden ser null");
 
		String pwdDigest = DigestUtils.md5Hex(password);
		User storedPwd = getUserFromDatabase(username, password);

		user.setRegisterSuccessful(pwdDigest.equals(storedPwd));
		user.setPassword(null);
		return user;
	
	}
 
	private User getUserFromDatabase(String username, String password) {
		User user = new User();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("No se puede conectar a la base de datos ",
					Response.Status.SERVICE_UNAVAILABLE);
		}
 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_USER_BY_USER);
			stmt.setString(1, username);
			stmt.setString(2, password);
 
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			} else
				throw new NotFoundException(username + " no encontrado. ");
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
	
	
	
	
	
	@Path("/follow/{username}/{followed}")
	@POST
	@Produces(MediaType2.PHOTO_API_USER)
	@Consumes(MediaType2.PHOTO_API_USER)
	public User FollowUuser(@PathParam("username") String username,@PathParam("followed") String followed) {
		
		User userfollowed = new User();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmtGetFollowed = null;
		PreparedStatement stmtInsertFollowed = null;

		try {
			stmtGetFollowed = conn.prepareStatement(GET_USER_BY_USERNAME);
			stmtGetFollowed.setString(1, followed);
			ResultSet rs = stmtGetFollowed.executeQuery();
			
			if (rs.next()) {
				userfollowed.setUsername(rs.getString("username"));
				userfollowed.setPassword(rs.getString("password"));
				userfollowed.setAvatar(rs.getInt("avatar"));
				
				conn.setAutoCommit(false);// desactivamos las confirmaciones
				// automaticas

				stmtInsertFollowed = conn.prepareStatement(INSERT_FOLLOW);
				stmtInsertFollowed.setString(1, username);
				stmtInsertFollowed.setString(2, followed);
				stmtInsertFollowed.executeUpdate();
				conn.commit();
				
			} else
				throw new NotFoundException(followed + " no encontrado. ");
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
				} 
		finally {
			try {
				if (stmtGetFollowed != null)
					stmtGetFollowed.close();

				if (stmtInsertFollowed != null)
					stmtInsertFollowed.close();
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {	
				
			}
		}
		return userfollowed;
	}
	
	@GET
	@Path("/following/{username}")
	@Produces(MediaType2.PHOTO_API_USER)
	@Consumes(MediaType2.PHOTO_API_USER)
	public UserCollection getUsersFollowing (@PathParam("username") String username){
		UserCollection users = new UserCollection();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_USERS_FOLLOWING);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				User user=new User();
				user.setUsername(rs.getString("followed"));
				users.addUser(user);

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
		
		return users;
	}
	
}
	

