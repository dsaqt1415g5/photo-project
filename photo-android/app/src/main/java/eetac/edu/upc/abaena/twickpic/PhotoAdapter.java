package eetac.edu.upc.abaena.twickpic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import eetac.edu.upc.abaena.twickpic.api.Photo;

/**
 * Created by Alejandro on 08/01/2015.
 */
public class PhotoAdapter extends BaseAdapter {

    ArrayList<Photo> data;
    LayoutInflater inflater;

    public PhotoAdapter(Context context, ArrayList<Photo> data) {

        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    private static class ViewHolder {
        //TextView tvDescription;
        TextView tvUsername;
        TextView tvAutor;
        TextView tvName;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        //long prueba = Integer.valueOf(((Photo)getItem(position)).getIdphoto().replace(".png",""));
        //System.out.print(prueba);
        //String valor= Long.valueOf(((Photo)getItem(position)).getIdphoto());
        long prueba=1;
        return prueba;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //posicion, vista asociada a esa posicion,parent
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photos_raw_list, null); //cuando no hay datos pide al inflate que crre una interfaz con el layout
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tvUsername);
            viewHolder.tvAutor = (TextView) convertView
                    .findViewById(R.id.tvAutor);
           // viewHolder.tvDescription = (TextView) convertView
           //         .findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        } //obtengo valores de los datos
        String name = data.get(position).getName();
        String username = data.get(position).getUser();
        String description = data.get(position).getDescription();
        String autor = data.get(position).getAutor();
        viewHolder.tvName.setText(name); //se le da el valor a la UI
        viewHolder.tvUsername.setText(username);
        viewHolder.tvAutor.setText(autor);
        //viewHolder.tvDescription.setText(description);
        return convertView;
    }
}
