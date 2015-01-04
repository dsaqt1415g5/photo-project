package edu.upc.eetac.dsa.abaena.photo.api;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;



public class PhotoApplication extends ResourceConfig{
	
	public PhotoApplication() {
		super();
		register(DeclarativeLinkingFeature.class);
		register(MultiPartFeature.class);
		
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			property(key, bundle.getObject(key));
		}
		
	}
	
}
