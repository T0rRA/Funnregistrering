package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

import androidx.core.content.FileProvider;

public class EmailIntent {

    //Opens the email app and fills the email.
    public static void sendEmail(String to, String subject, String message, int pictureNr, Context context) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("application/image");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to}); //To is the email address of the receiver of the email
        email.putExtra(Intent.EXTRA_SUBJECT, subject); //The email subject
        email.putExtra(Intent.EXTRA_TEXT, message); //The email text

        //If the pictureNr == 0 then the picture has not been set
        if(pictureNr!= 0) {
            //Getting the image file
            File file = new File(ImageSaver.getImagePath(context, pictureNr));
            //Attaching the image to the email
            email.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", file));
        }

        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //Permission needed to access files within he app

        context.startActivity(Intent.createChooser(email, "Choose an Email client :")); //Opening the email app (or the choose app menu, if default is not set on the device)
    }
}
