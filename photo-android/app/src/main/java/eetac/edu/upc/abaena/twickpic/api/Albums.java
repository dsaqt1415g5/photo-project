package eetac.edu.upc.abaena.twickpic.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class Albums {

    private int idalbum;
    private String nombre;
    private String description;
    private String username;
    private Map<String, Link> links = new HashMap<String, Link>();

    public int getIdalbum() {
        return idalbum;
    }

    public void setIdalbum(int idalbum) {
        this.idalbum = idalbum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }


}
