package com.bachelor_group54.funnregistrering;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nyeFunnBtn(View view) {
        Intent newActivityIntent = new Intent(MainActivity.this, RegistrereFunnActivity.class);
        MainActivity.this.startActivity(newActivityIntent);
    }

    public void mineFunnBtn(View view) {
    }

    public void infoBtn(View view) {
    }
}