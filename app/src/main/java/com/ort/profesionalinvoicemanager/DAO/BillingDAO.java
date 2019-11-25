package com.ort.profesionalinvoicemanager.DAO;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;

import com.ort.profesionalinvoicemanager.model.base.ApplicationContext;
import com.ort.profesionalinvoicemanager.views.MainActivity;

import java.io.Serializable;

public class BillingDAO  {

    public void SendEmail(PdfDocument pdf, Context context){
        String uriText =
                "mailto:pablopodgaiz@gmail.com" +
                        "?subject=" + Uri.encode("test subject");
        Uri uri = Uri.parse(uriText);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);

        Intent i = Intent.createChooser(emailIntent, "Send email to the developer...");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent .putExtra(Intent.EXTRA_STREAM, (Serializable) pdf);
        context.startActivity(i);

    }
}
