package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

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
