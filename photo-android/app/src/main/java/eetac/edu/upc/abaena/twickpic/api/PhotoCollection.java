package eetac.edu.upc.abaena.twickpic.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class PhotoCollection {

    private List<Photo> photos = new ArrayList<>();
    private Map<String, Link> links = new HashMap<String, Link>();

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }


}
