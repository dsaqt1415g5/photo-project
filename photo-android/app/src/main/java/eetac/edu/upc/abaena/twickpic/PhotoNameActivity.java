package eetac.edu.upc.abaena.twickpic;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import eetac.edu.upc.abaena.twickpic.api.AppException;
import eetac.edu.upc.abaena.twickpic.api.Photo;
import eetac.edu.upc.abaena.twickpic.api.PhotoAPI;
import eetac.edu.upc.abaena.twickpic.api.PhotoCollection;

/**
 * Created by Alejandro on 14/01/2015.
 */
public class PhotoNameActivity extends ListActivity {

    private class FetchPhotoNameTask extends
            AsyncTask<String, Void, PhotoCollection> {
        private ProgressDialog pd;

        @Override
        protected PhotoCollection doInBackground(String... params) {
            PhotoCollection photos = null;
            try {
                photos = PhotoAPI.getInstance(PhotoNameActivity.this).getPhotosByName(params[0]);
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
            pd = new ProgressDialog(PhotoNameActivity.this);
            pd.setTitle("Buscando...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    private final static String TAG = TwickpicMainActivity.class.toString();
    private ArrayList<Photo> PhotoList;
    private PhotoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_twickpic_main);
        String photoname = (String) getIntent().getExtras().get("nombrephoto");

        (new FetchPhotoNameTask()).execute(photoname);
        PhotoList = new ArrayList<Photo>();
        adapter = new PhotoAdapter(this, PhotoList);
        setListAdapter(adapter);

    }

    private void addPhotos(PhotoCollection photos){
        PhotoList.clear();
        PhotoList.addAll(photos.getPhotos());
        adapter.notifyDataSetChanged();
    }

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

    public void clickMe(View v) {

        //EditText et1=(EditText)findViewById(R.id.inputSearch);
        //String name = et1.getText().toString();
        //startPhotosActivity(name);

    }

    private void startPhotosActivity(String name) {
        //metodo para lanzar la actividad
        Intent intent = new Intent(this, PhotoNameActivity.class);
        intent.putExtra("nombrephoto",name);
        startActivity(intent);
        finish(); // se acaba el login para que al darle al boton back no pida otra vez el login
    }
}
