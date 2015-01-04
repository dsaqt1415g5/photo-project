package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;

public class ComentCollection {
	
	@InjectLinks({
		@InjectLink(resource=PhotoResource.class, style= Style.ABSOLUTE, rel="comment collection self", title="lista de comentarios", type=MediaType2.PHOTO_API_COMENT_COLLECTION),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="select comment", title="comment", type=MediaType2.PHOTO_API_COMENT)})

	private List<Link> links; //lista de atributos
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	private List<Coment> comments;

	public List<Coment> getComments() {
		return comments;
	}
	
	public ComentCollection() {
		super();
		comments = new ArrayList<>();
	}

	public void setComments(List<Coment> comments) {
		this.comments = comments;
	}
	public void addComment(Coment comment) {
		comments.add(comment);
	}

}
