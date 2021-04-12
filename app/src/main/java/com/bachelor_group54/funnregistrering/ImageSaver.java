package com.bachelor_group54.funnregistrering;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//This class is used to save and load images form local device storage
public class ImageSaver {

    public static void saveImage(Bitmap bitmap, Context context, int pictureNr) {
        File file = new File (getImagePath(context, pictureNr)); // Combines the program path and the filename

        try (FileOutputStream out = new FileOutputStream(file)) { //Opens the filoutputstream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a loss less format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImagePath(Context context, int pictureNr){
        String path = context.getFilesDir().getPath(); //Gets the path to the program
        String filename = "/Image-" + pictureNr +".jpg"; //Sets the image name
        return path + filename;
    }

    public static Bitmap loadImage(Context context, int pictureNr){
        if(pictureNr == 0){return null;} //Returns empty bitmap if the pictureNr is not set
        return BitmapFactory.decodeFile(getImagePath(context, pictureNr));
    }

    //Makes Bitmap form Base64
    public static Bitmap makeBitmapFormBase64(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    //Makes Base64 string for the database people
    public static String makeBase64FromBitmap(Bitmap picture){
        if(picture == null){return "";}

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
