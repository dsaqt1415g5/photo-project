package eetac.edu.upc.abaena.twickpic.api;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Alejandro on 05/01/2015.
 */
public class PhotoAPI {

    private final static String TAG = PhotoAPI.class.getName();
    private static PhotoAPI instance = null;
    private URL url;
    private PhotoRootAPI rootAPI = null;

    private PhotoAPI(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("twickpic.home");
        url = new URL(urlHome);
        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public final static PhotoAPI getInstance(Context context) throws AppException {
        if (instance == null)
            try {
                instance = new PhotoAPI(context);
            } catch (IOException e) {
                throw new AppException(
                        "Can't load configuration file");
            }
        return instance;
    }

    private void getRootAPI() throws AppException {
        Log.d(TAG, "getRootAPI()");
        rootAPI = new PhotoRootAPI(); //llama la instancia del modelo que tiene la raiz del servicio
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection(); //abrir la conexion
            urlConnection.setRequestMethod("GET"); //indica tipo de metodo
            urlConnection.setDoInput(true); //indica que vas a leer la respuesta que te envie
            urlConnection.connect(); //peticion a la base de datos
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;  //leer la respuesta
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) { //lee la respuesta y se guarda en un stringbuilder
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());  //se procesa el JSON de la respuesta
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

    }

    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map) //pasamos array y un mapa
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser
                        .parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s");
            for (String s : rels)
                map.put(s, link);
        }
    }

    public User getUser(String username, String password) throws AppException{
        User user=new User();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("users").getTarget()+"/login/"+username+"/"+password).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonUser = new JSONObject(sb.toString());
            // sting.setAuthor(jsonSting.getString("author"));
            user.setUsername(jsonUser.getString("username"));
            //user.setPassword(jsonUser.getString("password"));
            //JSONArray jsonLinks = jsonUser.getJSONArray("links");
            //parseLinks(jsonLinks, user.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }



        return user;
    }

    public PhotoCollection getPhotos() throws AppException{
        Log.d(TAG,"getfotos");
        PhotoCollection photos = new PhotoCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("photos").getTarget()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, photos.getLinks());

            JSONArray jsonPhotos = jsonObject.getJSONArray("photos");
            for (int i = 0; i < jsonPhotos.length(); i++) {
                Photo photo = new Photo();
                JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);
                // sting.setAuthor(jsonSting.getString("author"));
                photo.setIdphoto(jsonPhoto.getString("idphoto"));
                photo.setName(jsonPhoto.getString("name"));
                photo.setAutor(jsonPhoto.getString("autor"));
                photo.setUser(jsonPhoto.getString("user"));
                photo.setDescription(jsonPhoto.getString("description"));
                photo.setPhotoURL(jsonPhoto.getString("url"));
                photo.setTimestamp(jsonPhoto.getLong("timestamp"));
                jsonLinks = jsonPhoto.getJSONArray("links");
                parseLinks(jsonLinks, photo.getLinks());
                photos.getPhotos().add(photo);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return photos;
    }


    public PhotoCollection getPhotosByUser(String username) throws AppException{
        Log.d(TAG,"getImages()");
        System.out.print("pataaataaaa");
        PhotoCollection photos = new PhotoCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("photos").getTarget()+"/user/"+username).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, photos.getLinks());

            JSONArray jsonPhotos = jsonObject.getJSONArray("photos");
            for (int i = 0; i < jsonPhotos.length(); i++) {
                Photo photo = new Photo();
                JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);
                // sting.setAuthor(jsonSting.getString("author"));
                photo.setIdphoto(jsonPhoto.getString("idphoto"));
                photo.setName(jsonPhoto.getString("name"));
                photo.setAutor(jsonPhoto.getString("autor"));
                photo.setUser(jsonPhoto.getString("user"));
                photo.setDescription(jsonPhoto.getString("description"));
                //photo.setTimestamp(jsonPhoto.getLong("timestamp"));
                jsonLinks = jsonPhoto.getJSONArray("links");
                parseLinks(jsonLinks, photo.getLinks());
                photos.getPhotos().add(photo);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return photos;
    }


    public PhotoCollection getPhotosByCategory(String catergory) throws AppException{
        Log.d(TAG,"getImages()");
        PhotoCollection photos = new PhotoCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("photos").getTarget()+"/category/"+catergory
            ).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, photos.getLinks());

            JSONArray jsonPhotos = jsonObject.getJSONArray("photos");
            for (int i = 0; i < jsonPhotos.length(); i++) {
                Photo photo = new Photo();
                JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);
                // sting.setAuthor(jsonSting.getString("author"));
                photo.setIdphoto(jsonPhoto.getString("idphoto"));
                photo.setName(jsonPhoto.getString("name"));
                photo.setAutor(jsonPhoto.getString("autor"));
                photo.setUser(jsonPhoto.getString("user"));
                photo.setDescription(jsonPhoto.getString("description"));
                //photo.setTimestamp(jsonPhoto.getLong("timestamp"));
                jsonLinks = jsonPhoto.getJSONArray("links");
                parseLinks(jsonLinks, photo.getLinks());
                photos.getPhotos().add(photo);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return photos;
    }

    public PhotoCollection getPhotosByName(String photoname) throws AppException{
        Log.d(TAG,"getImages()");
        PhotoCollection photos = new PhotoCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("photos").getTarget()+"/title/"+photoname
            ).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, photos.getLinks());

            JSONArray jsonPhotos = jsonObject.getJSONArray("photos");
            for (int i = 0; i < jsonPhotos.length(); i++) {
                Photo photo = new Photo();
                JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);
                // sting.setAuthor(jsonSting.getString("author"));
                photo.setIdphoto(jsonPhoto.getString("idphoto"));
                photo.setName(jsonPhoto.getString("name"));
                photo.setAutor(jsonPhoto.getString("autor"));
                photo.setUser(jsonPhoto.getString("user"));
                photo.setDescription(jsonPhoto.getString("description"));
                //photo.setTimestamp(jsonPhoto.getLong("timestamp"));
                jsonLinks = jsonPhoto.getJSONArray("links");
                parseLinks(jsonLinks, photo.getLinks());
                photos.getPhotos().add(photo);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return photos;
    }

    public CategoriesCollection getCategories() throws AppException{
        Log.d(TAG,"getCategories()");
        CategoriesCollection categories = new CategoriesCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("categories").getTarget()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, categories.getLinks());

            JSONArray jsonCategories = jsonObject.getJSONArray("categories");
            for (int i = 0; i < jsonCategories.length(); i++) {
                Categories category = new Categories();
                JSONObject jsonCategory = jsonCategories.getJSONObject(i);
                category.setNombre(jsonCategory.getString("nombre"));
                jsonLinks = jsonCategory.getJSONArray("links");
                parseLinks(jsonLinks, category.getLinks());
                categories.getCategories().add(category);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return categories;
    }




    public ComentCollection getCommentCollectionByIdPhoto(String urlComments) throws AppException{
        Log.d(TAG,"getCommentCollectionByIdPhoto");

        ComentCollection comments = new ComentCollection();
        urlComments=urlComments.replace(".png","");
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlComments);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            //por aqui peta
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, comments.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("comments");
            for (int i = 0; i < jsonComments.length(); i++) {
                Coment comment = new Coment();
                JSONObject jsonComment = jsonComments.getJSONObject(i);
                comment.setIdphoto(jsonComment.getString("idphoto"));
                comment.setIdcomment(jsonComment.getInt("idcomment"));
                comment.setUsername(jsonComment.getString("username"));
                comment.setContent(jsonComment.getString("content"));
                //comment.setCreationTimestamp(jsonComment.getLong("creationTimestamp"));
                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, comment.getLinks());
                comments.getComments().add(comment);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return comments;
    }

    public Photo getPhoto(String urlPhoto) throws AppException{
        Log.d(TAG,"getImages()");
        Photo photo = new Photo();
        urlPhoto=urlPhoto.replace(".png","");
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlPhoto);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Twickpic API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonPhoto = new JSONObject(sb.toString());
            // sting.setAuthor(jsonSting.getString("author"));
            photo.setIdphoto(jsonPhoto.getString("idphoto"));
            photo.setName(jsonPhoto.getString("name"));
            photo.setAutor(jsonPhoto.getString("autor"));
            photo.setDescription(jsonPhoto.getString("description"));
            photo.setUser(jsonPhoto.getString("user"));
            photo.setPhotoURL(jsonPhoto.getString("photoURL"));
            JSONArray jsonLinks = jsonPhoto.getJSONArray("links");
            parseLinks(jsonLinks, photo.getLinks());



        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Twickpic API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Photo Root API");
        }

        return photo;
    }


    public Coment writeComment(String content, String url, String user) throws AppException {
        Coment comment=new Coment();
        comment.setContent(content);
        comment.setUsername(user);
        String[] idphoto=url.split("/", 7);
        String id=idphoto[6];
        id=id.replace(".png","");
        comment.setIdphoto(id);
        HttpURLConnection urlConnection = null;
        try {
            JSONObject jsonComment = createJsonComment(comment);
            URL urlPostStings = new URL(rootAPI.getLinks().get("photos")
                    .getTarget());
            urlConnection = (HttpURLConnection) urlPostStings.openConnection();
            String mediatype="application/vnd.photo.api.coment+json";
            //String mediatype = rootAPI.getLinks().get("photos").getParameters().get("type");
            urlConnection.setRequestProperty("Accept",
                    mediatype);
            urlConnection.setRequestProperty("Content-Type",
                    mediatype);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonComment.toString());
            writer.close();
            //peta aqui
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            //jsonSting = new JSONObject(sb.toString());

            //sting.setAuthor(jsonSting.getString("author"));
            /*sting.setStingid(jsonSting.getInt("stingid"));
            sting.setLastModified(jsonSting.getLong("lastModified"));
            sting.setSubject(jsonSting.getString("subject"));
            sting.setContent(jsonSting.getString("content"));
            sting.setUsername(jsonSting.getString("username"));
            JSONArray jsonLinks = jsonSting.getJSONArray("links");
            parseLinks(jsonLinks, sting.getLinks());*/
        //} //catch (JSONException e) {
            //Log.e(TAG, e.getMessage(), e);
            //throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return comment;
    }

    private JSONObject createJsonComment(Coment comment) throws JSONException {
        JSONObject jsonComment = new JSONObject();
        jsonComment.put("content", comment.getContent());
        jsonComment.put("username", comment.getUsername());
        jsonComment.put("idphoto", comment.getIdphoto());
        return jsonComment;
    }


}
