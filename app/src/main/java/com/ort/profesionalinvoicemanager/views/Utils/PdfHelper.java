package com.ort.profesionalinvoicemanager.views.Utils;

import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import android.os.Environment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.TextStyle;
public class PdfHelper {

    public PdfDocument getPdf(Invoice invoice) {
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawCircle(50, 50, 30, paint);
//        paint.setColor(Color.BLACK);
        String text = invoice.getIndustry().toString() + "\n"
                        + invoice.getIndustry().getTaxInformation().toString() + "\n"
                        + invoice.getIndustry().getTaxInformation().getIva().toString() + "\n"
                        + invoice.getClient().toString() + "\n"
                        + invoice.toString();

        android.text.TextPaint paint= new android.text.TextPaint(Paint.ANTI_ALIAS_FLAG);
        int textWidth = canvas.getWidth() - (int) (100);
        StaticLayout textLayout = new StaticLayout(
                text, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        canvas.save();
        // calculate x and y position where your text will be placed


//        canvas.translate (viewBound (), viewBounds.centerY () - mTextLayout.getHeight() / 2);
        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
//        float x = (bitmap.getWidth() - textWidth)/2;
//        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
//        canvas.translate(x, y);
        textLayout.draw(canvas);

        canvas.restore();
        // finish the page
        document.finishPage(page);
        // draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
//        paint = new Paint();
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"factura.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
//            Toast.makeText(, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
//            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
        return document;
    }
}