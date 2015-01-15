package eetac.edu.upc.abaena.twickpic.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class PhotoRootAPI {

    private Map<String, Link> links;

    public PhotoRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }
}
