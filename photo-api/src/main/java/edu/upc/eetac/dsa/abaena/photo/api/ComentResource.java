package edu.upc.eetac.dsa.abaena.photo.api;

import javax.sql.DataSource;
import javax.ws.rs.Path;


@Path("/comments")
public class ComentResource {
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	

}
