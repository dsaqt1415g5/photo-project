package edu.upc.eetac.dsa.abaena.photo.api.model;

import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.abaena.photo.api.MediaType;

public class ComentCollection {
	
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
