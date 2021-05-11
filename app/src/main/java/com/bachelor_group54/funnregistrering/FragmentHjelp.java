package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;

import android.widget.Button;
import android.widget.TextView;

public class FragmentHjelp extends Fragment {
    private View view;
    private TextView textView;
    private TextView fredningsText;
    private TextView meldeText;
    private TextView samfunnsText;
    private Button fredningsBtn;
    private Button meldepliktBtn;
    private Button samfunnBtn;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hjelp, container, false); //Loads the page from the XML file

        textView = view.findViewById(R.id.LinkOut); //link
        textView.setMovementMethod(LinkMovementMethod.getInstance());//link

        fredningsText = view.findViewById(R.id.fredningTXT);
        fredningsText.setVisibility(View.GONE);
        fredningsBtn= view.findViewById(R.id.frednings_button);

        meldeText = view.findViewById(R.id.meldepliktTXT);
        meldeText.setVisibility(View.GONE);
        meldepliktBtn= view.findViewById(R.id.meldeplikt_button);

        samfunnsText = view.findViewById(R.id.samfunnTXT);
        samfunnsText.setVisibility(View.GONE);
        samfunnBtn = view.findViewById(R.id.samfunn_button);

        visFredningsTXT();
        viMeldepliktTXT();
        visSamfunnTXT();

        return view;
    }
    public void visFredningsTXT(){
        fredningsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fredningsText.setVisibility((fredningsText.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
            }
        });


    }
    public void viMeldepliktTXT(){
        meldepliktBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meldeText.setVisibility((meldeText.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
            }
        });


    }
    public void visSamfunnTXT(){
        samfunnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                samfunnsText.setVisibility((samfunnsText.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
            }
        });


    }

}
