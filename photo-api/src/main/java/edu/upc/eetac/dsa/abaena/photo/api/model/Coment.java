package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.abaena.photo.api.ComentResource;
import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoRootAPIResource;

public class Coment {
	
	@InjectLinks({
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="comentarios", title="lista de comentarios", type=MediaType2.PHOTO_API_COMENT ),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="photo comment", title="apuntar foto", type=MediaType2.PHOTO_API_PHOTO),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="selfEdit", title="editar comentario",  method="getCommentFromDataBase", bindings=@Binding(name ="idcomment", value="${instance.idcomment}"))
		})
	
	private List<Link> links; //lista de atributos
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	private int idcomment;
	private String username;
	private String idphoto;
	private long creationTimestamp;
	private String content;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public int getIdcomment() {
		return idcomment;
	}
	public void setIdcomment(int idcomment) {
		this.idcomment = idcomment;
	}

	public String getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(String idphoto) {
		this.idphoto = idphoto;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}