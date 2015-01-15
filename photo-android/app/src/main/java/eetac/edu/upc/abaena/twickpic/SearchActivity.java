package eetac.edu.upc.abaena.twickpic;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import eetac.edu.upc.abaena.twickpic.api.AppException;
import eetac.edu.upc.abaena.twickpic.api.Categories;
import eetac.edu.upc.abaena.twickpic.api.CategoriesCollection;
import eetac.edu.upc.abaena.twickpic.api.Photo;
import eetac.edu.upc.abaena.twickpic.api.PhotoAPI;
import eetac.edu.upc.abaena.twickpic.api.PhotoCollection;

/**
 * Created by Alejandro on 08/01/2015.
 */
public class SearchActivity extends ListActivity {

    private ArrayList<Categories> CategoryList;
    private CategoryAdapter categoryadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_layout);
        (new FetchCategoriesTask()).execute();
        CategoryList = new ArrayList<Categories>();
        categoryadapter = new CategoryAdapter(this, CategoryList);
        setListAdapter(categoryadapter);

    }


    private class FetchCategoriesTask extends
            AsyncTask<Void, Void, CategoriesCollection> {
        private ProgressDialog pd;

        @Override
        protected CategoriesCollection doInBackground(Void... params) {
            CategoriesCollection categories = null;
            try {
                categories = PhotoAPI.getInstance(SearchActivity.this).getCategories();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return categories;
        }

        @Override
        protected void onPostExecute(CategoriesCollection result) {
            //ArrayList<Photo> photos = new ArrayList<Photo>(result.getPhotos());
            addCategories(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

    }

    public void doSearch(View v){

        EditText et1=(EditText) findViewById(R.id.etSearch);
        String photoname=et1.getText().toString();
        startPhotoName(photoname);

    }

    private void addCategories(CategoriesCollection categories){
        CategoryList.clear();
        CategoryList.addAll(categories.getCategories());
        categoryadapter.notifyDataSetChanged();
    }
    private final static String TAG = SearchActivity.class.toString();

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Categories category = CategoryList.get(position);
        Log.d(TAG, category.getLinks().get("lista-photos").getTarget());
        String categoryname = category.getNombre();
        Intent intent = new Intent(this, PhotosCategoryActivity.class);
        intent.putExtra("categoria",categoryname);
        startActivity(intent);

    }

    public void startPhotoName(String photoname){

        Intent intent = new Intent(this, PhotoNameActivity.class);
        intent.putExtra("nombrephoto",photoname);
        startActivity(intent);

    }

}
