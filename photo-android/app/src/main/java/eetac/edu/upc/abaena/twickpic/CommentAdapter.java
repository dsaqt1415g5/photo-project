package eetac.edu.upc.abaena.twickpic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import eetac.edu.upc.abaena.twickpic.api.Coment;
import eetac.edu.upc.abaena.twickpic.api.Photo;

/**
 * Created by Alejandro on 11/01/2015.
 */
public class CommentAdapter extends BaseAdapter {

    private ArrayList<Coment> data;
    private LayoutInflater inflater;

    private static class ViewHolder {
        TextView tvContent;
        TextView tvUsername;
        TextView tvCreationTimestamp;

    }
    public CommentAdapter(Context context, ArrayList<Coment> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
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


        return Long.parseLong(((Photo) getItem(position)).getIdphoto());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//devueve ese layout qe hemos creado cn datos

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_comment, null);//listrowsting que habiamos creado
            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_Content);
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tv_Username);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        };

        String content = data.get(position).getContent();//recuperando valores de esa posicion
        String username = data.get(position).getUsername();

        viewHolder.tvContent.setText(content);
        viewHolder.tvUsername.setText(username);

        return convertView;
    }
}
