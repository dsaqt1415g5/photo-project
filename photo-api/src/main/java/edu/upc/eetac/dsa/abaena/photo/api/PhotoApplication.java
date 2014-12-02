package edu.upc.eetac.dsa.abaena.photo.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class PhotoApplication extends ResourceConfig{
	
	public PhotoApplication() {
		super();
		register(DeclarativeLinkingFeature.class);
		
	}

}
