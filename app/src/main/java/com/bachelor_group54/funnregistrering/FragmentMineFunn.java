package com.bachelor_group54.funnregistrering;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//Class for the my finds fragment
public class FragmentMineFunn extends Fragment {
    private View view;
    private int listSize;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_funn, container, false); //Inflates the view
        getFinds();
        LinearLayout navbarMineFunn = view.findViewById(R.id.navbar_mine_funn); //Gets the navbar layout for this view
        navbarMineFunn.setBackground(getContext().getDrawable(R.drawable.navbar_btn_selected_background)); //Setts color on the navbar indicating what page you are on
        return view; //Returns the this fragment's view
    }

    @Override
    //Updates the page
    public void onResume() {
        //getFinds();
        super.onResume();
    }

    //This method creates and sets a list containing all the finds.
    public void makeList(ArrayList<Funn> findsList){
        ListView listView = view.findViewById(R.id.list_view_mine_funn); //Finds the listView
        ListAdapter listAdapter = new ListAdapter(getContext(), findsList, (MainActivity)getActivity()); //Creates the listAdapter, the listAdapter is used to handle the elements within the listView
        listView.setAdapter(listAdapter); //Sets the ListAdapter
        listSize = findsList.size();
    }

//Gets the finds from the database and update the listView
    public void getFinds(){
        if(User.getInstance().getUsername() == null){waitForUser(); return;}
        startProgressBar();
        GetJSON getJSON = new GetJSON(this);

        User user = User.getInstance();

        System.out.println(user.getUsername() + user.getPassword() + user.getName());
        getJSON.execute("Funn/GetAllUserFunn?brukernavn=" + user.getUsername() + "&passord=" + user.getPassword(), "funnID", "image", "funndato", "kommune", "fylke", "funndybde", "gjenstand_markert_med", "koordinat", "datum", "areal_type");
    }

    public void stopProgressBar(){
        progressBar = view.findViewById(R.id.progress_bar_fragment_mine_funn);
        progressBar.setWillNotDraw(true);
    }

    public void startProgressBar(){
        progressBar = view.findViewById(R.id.progress_bar_fragment_mine_funn);
        progressBar.setWillNotDraw(false);
    }

    public int getListSize() {
        return listSize == 0 ? 1 : listSize;
    }

    //Waiting for the user to be saved, so we can load the finds.
    public void waitForUser(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                    if(User.getInstance().getUsername() == null){
                        waitForUser();
                    }
                    getFinds();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }).start();
    }
}
