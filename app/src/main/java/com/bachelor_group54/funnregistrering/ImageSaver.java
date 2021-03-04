package com.bachelor_group54.funnregistrering;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaver {

    public static void saveImage(Bitmap bitmap, Context context, int pictureNr) {

        String path =  context.getFilesDir().getPath();
        String filename = "Image-" + pictureNr +".jpg";
        File file = new File (path + filename);

        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImage(Context context, int pictureNr){
        String path = context.getFilesDir().getPath();
        String filename = "Image-" + pictureNr +".jpg";
        String file = path + filename;

        return BitmapFactory.decodeFile(file);
    }
}
