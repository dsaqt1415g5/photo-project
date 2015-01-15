package eetac.edu.upc.abaena.twickpic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import eetac.edu.upc.abaena.twickpic.api.AppException;
import eetac.edu.upc.abaena.twickpic.api.PhotoAPI;
import eetac.edu.upc.abaena.twickpic.api.User;

/**
 * Created by Alejandro on 08/01/2015.
 */

public class LoginActivity extends Activity {
    private final static String TAG = LoginActivity.class.getName();
    String username;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences prefs = getSharedPreferences("photo-profile",
                Context.MODE_PRIVATE);  //solo la aplicacion puede leer estas prefenrencias
        String username = prefs.getString("username", null); //recupera user y password y si no devuelve por defecto null
        String password = prefs.getString("password", null);



        setContentView(R.layout.login_layout); //en caso contrario ponemos el layout de la app
    }

    public void signIn(View v) {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        username = etUsername.getText().toString(); //obtiene user y pass
        String password = etPassword.getText().toString();

        // Launch a background task to check if credentials are correct
        // If correct, store username and password and start Beeter activity
        // else, handle error

        // I'll suppose that u/p are correct:

        (new FetchLoginTask()).execute(username,password);



        SharedPreferences prefs = getSharedPreferences("photo-profile",  //se obtiene el editor
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("username", username);  //se optiene user y pass
        editor.putString("password", password);
        boolean done = editor.commit(); //se pilla la confirmacion
        if (done)
            Log.d(TAG, "preferences set");
        else
            Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");

        finish(); // se lanza la main activity
    }

    private void startTwickpicActivity(String usuario) {  //metodo para lanzar la actividad
        Intent intent = new Intent(this, TwickpicMainActivity.class);
        intent.putExtra("username", usuario);
        startActivity(intent);
        finish(); // se acaba el login para que al darle al boton back no pida otra vez el login
    }

    private class FetchLoginTask extends
            AsyncTask<String, Void, User> {
        private ProgressDialog pd;

        @Override
        protected User doInBackground(String... params) {
            try {
                user = PhotoAPI.getInstance(LoginActivity.this).getUser(params[0],params[1]);
            } catch (AppException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User result) {
            //ArrayList<Photo> photos = new ArrayList<Photo>(result.getPhotos());
            //user=result;
            String usuario = result.getUsername();


            //String pass=user.getPassword();
            if ((usuario.equals(username))) {
                //Intent intent = new Intent(this, TwickpicMainActivity.class);
                startTwickpicActivity(usuario);
            }else{

                finish();
            }
            if (pd != null) {
                pd.dismiss();
            }

        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Iniciando Sesion...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }




}