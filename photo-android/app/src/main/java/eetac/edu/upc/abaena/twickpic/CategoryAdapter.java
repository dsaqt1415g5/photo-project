package eetac.edu.upc.abaena.twickpic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eetac.edu.upc.abaena.twickpic.api.Categories;

import static eetac.edu.upc.abaena.twickpic.R.drawable.futboliti;


/**
 * Created by Alejandro on 14/01/2015.
 */
public class CategoryAdapter extends BaseAdapter {

    private ArrayList<Categories> data;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, ArrayList<Categories> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    private static class ViewHolder {
        TextView tvCategory;
        ImageView ivCategoryPhoto;


    }
    @Override
    public int getCount() {//devuelve numero total de filas que habria en la lista , numero de datos q tu muestras

        return data.size();
    }

    @Override
    public Object getItem(int position) {//modelo, cada posicion de la lista tiene un modelo de la cual tiene una serie de datos

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {//devuelve valor unico para una determinada posicion

        return ((Categories) getItem(position)).getIdcategory();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_categories, null);//listrowsting que habiamos creado
            viewHolder = new ViewHolder();
            viewHolder.tvCategory = (TextView) convertView
                    .findViewById(R.id.tvCategory);
            viewHolder.ivCategoryPhoto = (ImageView) convertView
                    .findViewById(R.id.ivCategoryPhoto);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        };

        String categoryName = data.get(position).getNombre();//recuperando valores de esa posicion

        viewHolder.tvCategory.setText(categoryName);
        //viewHolder.ivCategoryPhoto.setImageResource(R.drawable.moda);

        if (categoryName=="futbol"){
            viewHolder.ivCategoryPhoto.setImageResource(R.drawable.futboliti);
        }else if (categoryName=="moda"){
            viewHolder.ivCategoryPhoto.setImageResource(R.drawable.moda);
        }else if (categoryName=="coches"){
            viewHolder.ivCategoryPhoto.setImageResource(R.drawable.coche);
        }


        /*switch (categoryName) {
            case "1": categoryName = "futbol";
                viewHolder.ivCategoryPhoto.setImageResource(R.drawable.futbol);
                break;
            case "2":  categoryName = "moda";
                viewHolder.ivCategoryPhoto.setImageResource(R.drawable.moda);
                break;
            case "3":  categoryName = "coches";
                viewHolder.ivCategoryPhoto.setImageResource(R.drawable.coche);
                break;

        }*/

        return convertView;
    }
}
