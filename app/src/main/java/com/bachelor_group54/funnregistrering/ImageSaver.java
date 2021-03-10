package com.bachelor_group54.funnregistrering;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//This class is used to save and load images form local device storage
public class ImageSaver {

    public static void saveImage(Bitmap bitmap, Context context, int pictureNr) {

        String path =  context.getFilesDir().getPath(); //Gets the path to the program
        String filename = "Image-" + pictureNr +".jpg"; //Sets the iamge name
        File file = new File (path + filename); // Combines the program path and the filename

        try (FileOutputStream out = new FileOutputStream(file)) { //Opens the filoutputstream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImage(Context context, int pictureNr){
        if(pictureNr == 0){new BitmapFactory();} //Returns empty bitmap if the pictureNr is not set

        String path = context.getFilesDir().getPath(); //Gets the path to the program
        String filename = "Image-" + pictureNr +".jpg"; //Sets the iamge name
        String file = path + filename; // Combines the program path and the filename

        return BitmapFactory.decodeFile(file);
    }
}
