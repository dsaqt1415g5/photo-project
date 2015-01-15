package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.abaena.photo.api.CategoriesResource;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoRootAPIResource;
import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;


public class PhotoRootAPI {
	@InjectLinks({
		@InjectLink(resource = PhotoRootAPIResource.class, style = Style.ABSOLUTE, rel = "self mark home", title = "PhotoRootAPI", method = "getRootAPI"),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="photos", title="lista de photos", type=MediaType.MULTIPART_FORM_DATA ),
		@InjectLink(resource = CategoriesResource.class, style = Style.ABSOLUTE, rel ="categories", title="lista de categorias",method="getCategoriesCollection", type=MediaType2.PHOTO_API_CATEGORIES_COLLECTION),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="create-photo", title="crear photo", type=MediaType.MULTIPART_FORM_DATA )})
	
	private List<Link> links; //lista de atributos
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
