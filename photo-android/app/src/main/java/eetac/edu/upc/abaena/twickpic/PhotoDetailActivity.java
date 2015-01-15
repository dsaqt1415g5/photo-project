package eetac.edu.upc.abaena.twickpic;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import eetac.edu.upc.abaena.twickpic.api.AppException;
import eetac.edu.upc.abaena.twickpic.api.Coment;
import eetac.edu.upc.abaena.twickpic.api.ComentCollection;
import eetac.edu.upc.abaena.twickpic.api.Photo;
import eetac.edu.upc.abaena.twickpic.api.PhotoAPI;

/**
 * Created by Alejandro on 09/01/2015.
 */
public class PhotoDetailActivity extends ListActivity {

    private ArrayList<Coment> CommentList;
    private CommentAdapter commentadapter;

    private class FetchPhotoTask extends AsyncTask<String, Void, Bitmap> {
        private ProgressDialog pd;
        ImageView imagenFoto;

        public FetchPhotoTask(ImageView imagenFoto) {
            this.imagenFoto = imagenFoto;
        }

        @Override
        protected Bitmap doInBackground(String... url) {

            String urldisplay = url[0];
            Bitmap bitmap = null;
            try {

                URL imageUrl = null;
                HttpURLConnection conn = null;
                imageUrl = new URL(urldisplay);
                conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                imagenFoto.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            imagenFoto.setImageBitmap(result);
            if (pd != null) {
                pd.dismiss();
            }

        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(PhotoDetailActivity.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }



    private final static String TAG = PhotoDetailActivity.class.getName();
    String urlPhoto;
    String us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.photo_detail_layout);

        urlPhoto = (String) getIntent().getExtras().get("url");
        String urlFoto = (String) getIntent().getExtras().get("urlFoto");
        urlFoto=urlFoto.replace(":8080/photo-api/photos/photo",":80/img");
        us=(String) getIntent().getExtras().get("usuario");

        String urlComments = (String) getIntent().getExtras().get("url2");
        (new FetchCommentsTask()).execute(urlComments);
        (new FetchPhotoTask((ImageView) findViewById(R.id.ImagenFoto))).execute(urlFoto);
        CommentList = new ArrayList<Coment>();
        commentadapter = new CommentAdapter(this, CommentList);
        setListAdapter(commentadapter);



    }

    private class FetchCommentsTask extends AsyncTask<String, Void, ComentCollection> {
        private ProgressDialog pd;

        @Override
        protected ComentCollection doInBackground(String... params) {
            ComentCollection comments = null;
            try {
                comments = PhotoAPI.getInstance(PhotoDetailActivity.this).getCommentCollectionByIdPhoto(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return comments;
        }

        @Override
        protected void onPostExecute(ComentCollection result) {

            ComentCollection perchi = result;
            addComments(result);

            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute(){

        }
    }

    private void addComments(ComentCollection comments){
        CommentList.clear();
        CommentList.addAll(comments.getComments());
        commentadapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_twickpic_main, menu);
        return true;
    }

    private final static int WRITE_ACTIVITY = 0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miWrite:
                Intent intent = new Intent(this, PhotoCommentsActivity.class);
                intent.putExtra("url_photo", urlPhoto);
                intent.putExtra("usuario",us);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case WRITE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String jsonComment = res.getString("selfPhoto"); //a los extras no se le pueden pasar objetos
                    Coment comment = new Gson().fromJson(jsonComment, Coment.class); //Gson es una api de json para convertir una cadena en objeto                                                                                 //se añade a la posicion 0 el sting que acabamos de crear
                    commentadapter.notifyDataSetChanged();
                }
                break;
        }
    }*/

}
