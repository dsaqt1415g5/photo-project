package edu.upc.eetac.dsa.abaena.photo.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.upc.eetac.dsa.abaena.photo.api.MediaType2;
import edu.upc.eetac.dsa.abaena.photo.api.model.PhotoError;

@Provider
public class WebApplicationExceptionMapper implements
		ExceptionMapper<WebApplicationException> {
	@Override
	public Response toResponse(WebApplicationException exception) {
		PhotoError error = new PhotoError(
				exception.getResponse().getStatus(), exception.getMessage());
		return Response.status(error.getStatus()).entity(error)
				.type(MediaType2.PHOTO_API_ERROR).build();
	}
}