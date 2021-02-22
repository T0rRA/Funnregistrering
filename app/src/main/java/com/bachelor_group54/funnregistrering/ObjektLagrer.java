package com.bachelor_group54.funnregistrering;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//Klasse for lagring og lasting av hele objekter
public class ObjektLagrer {
    private Context context;
    private String fileName;

    public ObjektLagrer(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public void saveData(ArrayList<Object> data){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        }catch (Exception ignored){}
    }

    public ArrayList<Object> loadData(){
        ArrayList<Object> data = null;
        try {
            FileInputStream fin = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            //Ikke egentlig unchecked siden man bare kan lagre ArrayList<Object> i første omgang
            data = (ArrayList<Object>) ois.readObject();
            ois.close();
        }catch (Exception ignored){}

        return data;
    }
}
