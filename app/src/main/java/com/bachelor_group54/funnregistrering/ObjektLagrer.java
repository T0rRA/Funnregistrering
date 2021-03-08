package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//This class is for saving and loading ArrayLists containing any type of object, from file.
//When using this class it is IMPORTANT that every class in the ArrayList and their children implements Serializable.
public class ObjektLagrer {
    private Context context;
    private String fileName;

    public ObjektLagrer(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    //Saves an ArrayList containing any object, but can only save one ArrayList with each filename,
    // an will overwrite if another is saved. If you want to add an object to the list you should load
    // the ArrayList then add the object, then save it again.
    public void saveData(ArrayList<Object> data){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            Toast.makeText(context, "Lagrer data..." , Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Gets the ArrayList saved as the filename
    public ArrayList<Object> loadData(){
        ArrayList<Object> data = null;
        try {
            FileInputStream fin = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            //Ignore the warning as the save method only lets you save ArrayList<Object> anyways.
            data = (ArrayList<Object>) ois.readObject();
            ois.close();
        }catch (Exception ignored){}

        //If data is still null then there is ether an IOException or no data is in the file.
        //Returns an empty ArrayList so the code can still work on first time devices.
        if(data == null){
            data = new ArrayList<>();
            System.out.println("--------------------------------------\nArray empty");
        }
        return data;
    }
}
