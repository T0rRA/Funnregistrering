package com.bachelor_group54.funnregistrering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextDialog extends androidx.fragment.app.DialogFragment {
    private View view;
    private int layoutId;
    private String text;

    public TextDialog(int layoutId, String text) {
        this.layoutId = layoutId;
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layoutId, container, false);
        TextView textView = view.findViewById(R.id.dialog_tv);
        textView.setText(text);
        return view;
    }

    @Override
    public View getView() {
        return view;
    }

}
