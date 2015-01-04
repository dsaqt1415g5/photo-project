package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.abaena.photo.api.CategoriesResource;
import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;

public class Categories{
	
	@InjectLinks({
		@InjectLink(resource=CategoriesResource.class, style= Style.ABSOLUTE, rel="category collection", title="lista de categorias", type=MediaType2.PHOTO_API_CATEGORIES_COLLECTION),
		@InjectLink(resource = PhotoResource.class, style = Style.ABSOLUTE, rel ="lista-photos", title="lista fotos categoria", type=MediaType.MULTIPART_FORM_DATA, method="getImagesByCategory", bindings=@Binding(name="category", value="${instance.nombre}"))
		})

	private List<Link> links; //lista de atributos
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}

private int idcategory;
private String nombre;

public void setIdcategory(int idcategory){
this.idcategory=idcategory;
}

public int getIdcategory(){
return idcategory;}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}









}