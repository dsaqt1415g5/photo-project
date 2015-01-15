package eetac.edu.upc.abaena.twickpic.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class AlbumsCollection {

    private List<Albums> albums;
    private Map<String, Link> links = new HashMap<String, Link>();

    public List<Albums> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Albums> albums) {
        this.albums = albums;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }


}
