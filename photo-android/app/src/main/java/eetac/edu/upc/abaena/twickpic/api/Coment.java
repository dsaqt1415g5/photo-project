package eetac.edu.upc.abaena.twickpic.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class Coment {

        private int idcomment;
        private String username;
        private String idphoto;
        private long creationTimestamp;
        private String content;
    private Map<String, Link> links = new HashMap<String, Link>();

    public int getIdcomment() {
        return idcomment;
    }

    public void setIdcomment(int idcomment) {
        this.idcomment = idcomment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdphoto() {
        return idphoto;
    }

    public void setIdphoto(String idphoto) {
        this.idphoto = idphoto;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }




}
