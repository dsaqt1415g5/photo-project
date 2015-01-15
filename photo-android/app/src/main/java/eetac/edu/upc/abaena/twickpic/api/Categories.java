package eetac.edu.upc.abaena.twickpic.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class Categories {
    private int idcategory;
    private String nombre;
    private Map<String, Link> links = new HashMap<String, Link>();

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }


}
