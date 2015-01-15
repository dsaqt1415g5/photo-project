package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;

public class Photo {
	
	@InjectLinks({
		@InjectLink(resource=PhotoResource.class, style = Style.ABSOLUTE, rel="photos", title="photo collection puntero", type = MediaType.MULTIPART_FORM_DATA),
		@InjectLink(resource=PhotoResource.class, style = Style.ABSOLUTE, rel="selfPhoto", title="PhotoPuntero", type = MediaType.MULTIPART_FORM_DATA, method="getImagen", bindings=@Binding(name ="idphoto", value="${instance.idphoto}")),
		@InjectLink(resource=PhotoResource.class, style = Style.ABSOLUTE, rel="urlPhoto", title="URL photo", type = MediaType.MULTIPART_FORM_DATA, method="getImagen", bindings=@Binding(name ="photoURL", value="${instance.photoURL}")),
		@InjectLink(resource=PhotoResource.class, style = Style.ABSOLUTE, rel="photoComments", title="ComentsPuntero", type=MediaType2.PHOTO_API_COMENT_COLLECTION, method="getCommentCollectionByIdPhoto", bindings=@Binding(name="idphoto", value="${instance.idphoto}"))
	})
	
	 
	
	private List<Link> links; //lista de atributos
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	private String idphoto;
	private String user;
	private String autor;
	private String file;
	private String name;
	private String description; 
	private long timestamp;
	private String photoURL;
	
	
	
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getIdphoto() {
		return idphoto;
	}
	public void setIdphoto(String idphoto) {
		this.idphoto = idphoto;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	
	
}
