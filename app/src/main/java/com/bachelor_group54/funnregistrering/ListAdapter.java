package com.bachelor_group54.funnregistrering;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Funn> itemList;

    public ListAdapter(Context context, ArrayList<Funn> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    //How many items are in the data set represented by the Adapter
    public int getCount() {
        if(itemList.size() == 0){
            return 1;
        }
        return itemList.size();
    }

    @Override
    //Get the data item associated with the specified position in the data set
    public Object getItem(int i) {
        if(i > itemList.size()-1){
            return null;
        }
        return itemList.get(i);
    }

    @Override
    //Get the row id associated with the specified position in the list
    public long getItemId(int i) {
        return 0;
    }


    @Override
    //The method called by the list view when using the setAdapter method, to display the list on the screen
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Writes message if list is empty
        if(itemList.size() < 1){
            TextView textView = new TextView(context);
            textView.setText(R.string.ingenting_i_listen);
            textView.setTextSize(25);
            textView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            textView.setPadding(50,50,50,50);
            return textView;
        }

        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflator = ((Activity) context).getLayoutInflater();

        convertView = inflator.inflate(R.layout.mine_funn_liste_item, null);


        viewHolder.textViewTitel = convertView.findViewById(R.id.titel_mine_funn_liste_item); //Gets the title textView of the list item
        viewHolder.textViewTitel.setText(itemList.get(position).getTittel()); //Sets the title of the list item

        viewHolder.textViewDato = convertView.findViewById(R.id.dato_mine_funn_liste_item); //Gets the date textView of the list item
        viewHolder.textViewDato.setText(itemList.get(position).getDato()); //Sets the date of the list item

        viewHolder.textViewSted = convertView.findViewById(R.id.sted_mine_funn_liste_item); //Gets the location textView of the list item
        viewHolder.textViewSted.setText(itemList.get(position).getKommune()); //Sets the location of the list item

        viewHolder.picture = convertView.findViewById(R.id.image_mine_funn_liste_item); //Gets the image View of the list item
        viewHolder.picture.setImageBitmap(ImageSaver.loadImage(context,itemList.get(position).getBilde())); //Sets the image of the list item

        //Creates empty elements to maintain scroll for larger lists
        if(itemList.get(position) == null){
            viewHolder.textViewTitel.setVisibility(View.GONE);
            viewHolder.textViewDato.setVisibility(View.GONE);
            viewHolder.textViewSted.setVisibility(View.INVISIBLE);
        }

        return convertView; //Returns the view
    }

    //The ViewHolder is a class that holds the views that are contained within each list entry
    private static class ViewHolder {
        private ImageView picture;
        private TextView textViewTitel;
        private TextView textViewDato;
        private TextView textViewSted;
    }
}
