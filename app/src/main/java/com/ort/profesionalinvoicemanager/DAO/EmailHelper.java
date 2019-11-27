package com.ort.profesionalinvoicemanager.DAO;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;

public class EmailHelper {


   public static void sendEmail(String subject,String body,String destiny,String title, Context context){
//        String uriText =
//                "mailto:pablopodgaiz@gmail.com" +
//                        "?subject=" + Uri.encode("test subject");
//        Uri uri = Uri.parse(uriText);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/octet-stream");
        String to[] = {destiny};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.startActivity(Intent.createChooser(emailIntent,title));
    }
}
