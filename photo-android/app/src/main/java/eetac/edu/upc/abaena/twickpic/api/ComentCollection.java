package eetac.edu.upc.abaena.twickpic.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class ComentCollection {

    private List<Coment> comments = new ArrayList<>();
    private Map<String, Link> links = new HashMap<String, Link>();

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public List<Coment> getComments() {
        return comments;
    }

    public void setComments(List<Coment> comments) {
        this.comments = comments;
    }


}
