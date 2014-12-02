package edu.upc.eetac.dsa.abaena.photo.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import edu.upc.eetac.dsa.abaena.photo.api.model.PhotoRootAPI;

@Path("/")
public class PhotoRootAPIResource {
	@GET
	public PhotoRootAPI getRootAPI() {
		PhotoRootAPI api = new PhotoRootAPI();
		return api;
	}
}
