package eetac.edu.upc.abaena.twickpic;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


//import com.google.gson.Gson;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import eetac.edu.upc.abaena.twickpic.api.AppException;
import eetac.edu.upc.abaena.twickpic.api.Photo;
import eetac.edu.upc.abaena.twickpic.api.PhotoAPI;
import eetac.edu.upc.abaena.twickpic.api.PhotoCollection;



public class TwickpicMainActivity extends ListActivity {

    String serverAddress;
    String serverPort;

    //metodo para obtener las fotos en background
    private class FetchPhotosTask extends
            AsyncTask<Void, Void, PhotoCollection> {
        private ProgressDialog pd;

        @Override
        protected PhotoCollection doInBackground(Void... params) {
            PhotoCollection photos = null;
            try {
             photos = PhotoAPI.getInstance(TwickpicMainActivity.this).getPhotosByUser();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return photos;
        }

        @Override
        protected void onPostExecute(PhotoCollection result) {
            //ArrayList<Photo> photos = new ArrayList<Photo>(result.getPhotos());
            addPhotos(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(TwickpicMainActivity.this);
            pd.setTitle("Buscando...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }


    private void addPhotos(PhotoCollection photos){
        PhotoList.clear();
        PhotoList.addAll(photos.getPhotos());
        adapter.notifyDataSetChanged();
    }


    private ArrayList<Photo> PhotoList;
    private PhotoAdapter adapter;
    private final static String TAG = TwickpicMainActivity.class.toString();

    //creando el boton de opciones

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_twickpic_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Buscar:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent); //se espera un resultado de la actividad que lanzamos
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //metodo para mostrar las fotos obtenidas

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twickpic_main);

        SharedPreferences prefs = getSharedPreferences("twickpic-profile",
                Context.MODE_PRIVATE);

        final String username = prefs.getString("username", null);
        final String password = prefs.getString("password", null);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password
                        .toCharArray());
            }
        });
        Log.d(TAG, "authenticated with " + username + ":" + password);
        System.out.print(username +" "+ password);
        AssetManager assetManager = getAssets();
        Properties config = new Properties();
        try {
            config.load(assetManager.open("config.properties"));
            serverAddress = config.getProperty("server.address");
            serverPort = config.getProperty("server.port");

            Log.d(TAG, "Configured server " + serverAddress + ":" + serverPort);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            finish();
        }
        PhotoList = new ArrayList<Photo>();
        adapter = new PhotoAdapter(this, PhotoList);
        setListAdapter(adapter);
        (new FetchPhotosTask()).execute();


    }

    private final static int WRITE_ACTIVITY = 0;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Photo photo = PhotoList.get(position);
        Log.d(TAG, photo.getLinks().get("selfPhoto").getTarget());
        String urlphoto = photo.getPhotoURL();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra("url",photo.getLinks().get("selfPhoto").getTarget());
        intent.putExtra("url2",photo.getLinks().get("photoComments").getTarget());
        intent.putExtra("urlFoto",photo.getLinks().get("urlPhoto").getTarget());
        startActivity(intent);

    }

}
