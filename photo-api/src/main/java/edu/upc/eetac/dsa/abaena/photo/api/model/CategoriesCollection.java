package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.abaena.photo.api.CategoriesResource;
import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.PhotoResource;

public class CategoriesCollection {
	


	private List<Categories> categories;

	public List<Categories> getCategories() {
		return categories;
	}

	
	public CategoriesCollection() {
		super();
		categories = new ArrayList<>();
	}
	
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
	
		
	public void addCategories(Categories category){
		
		categories.add(category);
		
	}



	
}
