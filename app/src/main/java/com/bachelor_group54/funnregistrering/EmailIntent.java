package com.bachelor_group54.funnregistrering;

import android.content.Context;
import android.content.Intent;

public class EmailIntent {

    //Opens the email app and fills the email.
    public static void sendEmail(String to, String subject, String message, Context context) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to}); //To is the email address of the receiver of the email
        email.putExtra(Intent.EXTRA_SUBJECT, subject); //
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}
