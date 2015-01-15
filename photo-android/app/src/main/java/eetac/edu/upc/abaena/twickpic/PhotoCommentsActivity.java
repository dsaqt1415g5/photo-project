package eetac.edu.upc.abaena.twickpic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import eetac.edu.upc.abaena.twickpic.api.AppException;
import eetac.edu.upc.abaena.twickpic.api.Coment;
import eetac.edu.upc.abaena.twickpic.api.PhotoAPI;

/**
 * Created by Alejandro on 15/01/2015.
 */
public class PhotoCommentsActivity extends Activity {
    String urlPhoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_comment_layout);

        urlPhoto = (String) getIntent().getExtras().get("url_photo");
    }

    public void postComment(View v) {
        EditText etContent = (EditText) findViewById(R.id.inputComment);

        String content = etContent.getText().toString();

        (new PostCommentTask()).execute(content, urlPhoto);
    }

    private class PostCommentTask extends AsyncTask<String, Void, Coment> {
        private ProgressDialog pd;
        Coment comment = null;

        @Override
        protected Coment doInBackground(String... params) {
            Coment comment = null;
            try {
                comment = PhotoAPI.getInstance(PhotoCommentsActivity.this).writeComment(params[0], params[1]);

            } catch (AppException e) {
                e.printStackTrace();
            }
            return comment;
        }

        @Override
        protected void onPostExecute(Coment result) {
            backPhotoDetail();
            if (pd != null) {
                pd.dismiss();
            }
        }
    }

    private void backPhotoDetail(){

        //finish();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        //intent.putExtra("urlFoto",urlPhoto);
        startActivity(intent);
    }
}