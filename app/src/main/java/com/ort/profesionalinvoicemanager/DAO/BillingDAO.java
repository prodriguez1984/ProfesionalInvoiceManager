package com.ort.profesionalinvoicemanager.DAO;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;

import com.ort.profesionalinvoicemanager.model.base.ApplicationContext;
import com.ort.profesionalinvoicemanager.views.MainActivity;

import java.io.File;
import java.io.Serializable;

public class BillingDAO  {

    public void SendEmail(String path, Context context){
//        String uriText =
//                "mailto:pablopodgaiz@gmail.com" +
//                        "?subject=" + Uri.encode("test subject");
//        Uri uri = Uri.parse(uriText);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/octet-stream");
        String to[] = {"pablopodgaiz@gmail.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Factura");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(emailIntent,"Factura"));

    }
}
