package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoRootAPIResource;

public class PhotoCollection {
	
	@InjectLinks({
		@InjectLink(resource=PhotoResource.class, style= Style.ABSOLUTE, rel="photo collection self", title="lista de fotos", type=MediaType.MULTIPART_FORM_DATA),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="create-photo", title="crear photo", type=MediaType.MULTIPART_FORM_DATA )})

	private List<Link> links; //lista de atributos
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	private List<Photo> photos = new ArrayList<>();

	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
}
