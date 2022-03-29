package com.omsoftonics.debtcred.helper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.omsoftonics.debtcred.BuildConfig;
import com.omsoftonics.debtcred.R;
import com.omsoftonics.debtcred.model.Datad;
import com.omsoftonics.debtcred.model.EventInformation;
import com.omsoftonics.debtcred.model.Expanditures;
import com.omsoftonics.debtcred.model.VarganiReciepts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


public class PrintPDFInformation {


    public int PAGE_WIDTH=1200;
    public int PAGE_HEIGHT=2010;
    AppCompatActivity context;

    int totalVarganiCollected=0;
    int totalVarganiYetToCome=0;
    int totalExpenseDone=0;
    int total=0;
    public PrintPDFInformation(AppCompatActivity activity) {
        this.context=activity;
    }


    public void printToPdf(EventInformation eventInformation) throws IOException {

        String pdfPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File file=new File(pdfPath,"Vargani_Event_"+eventInformation.geteventName()+'_'+eventInformation.getDateCreated().replace('/','_')+".pdf");


        OutputStream outputStream=new FileOutputStream(file);


        PdfDocument myPdfDocument=new PdfDocument();




        //page 1
        PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,1).create();
        PdfDocument.Page myPage1= myPdfDocument.startPage(myPageInfo1);
        Canvas canvas=myPage1.getCanvas();

        // Page


        Paint titlePaint=new Paint();

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(90);
        canvas.drawText(eventInformation.geteventName(),PAGE_WIDTH/2,PAGE_HEIGHT/2,titlePaint);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(70);
        canvas.drawText("Report",PAGE_WIDTH/2,PAGE_HEIGHT/2+90,titlePaint);



        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        canvas.drawText("Generated By Vargani Android App",PAGE_WIDTH/2,PAGE_HEIGHT/2+170,titlePaint);


        canvas.drawText("Om Softonics",PAGE_WIDTH/2,PAGE_HEIGHT-100,titlePaint);



        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.varganilogo);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        canvas.drawBitmap(icon,PAGE_WIDTH/2-300,200,titlePaint);


        myPdfDocument.finishPage(myPage1);




        //page 2
        PdfDocument.PageInfo myPageInfo2=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,2).create();
        PdfDocument.Page myPage2= myPdfDocument.startPage(myPageInfo2);
        Canvas canvas2=myPage2.getCanvas();

        // Page

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(80);
        canvas2.drawText("Basic Report",PAGE_WIDTH/2,200,titlePaint);






        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Vargani Collected : ",PAGE_WIDTH/2-350,400,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(50);
        canvas2.drawText(""+(eventInformation.getEventIncome()+eventInformation.getEventExp()),PAGE_WIDTH/2,400,titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Vargani Balance : ",PAGE_WIDTH/2-350,500,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(50);
        canvas2.drawText(""+eventInformation.getEventIncome(),PAGE_WIDTH/2,500,titlePaint);




        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Vargani Pending : ",PAGE_WIDTH/2-350,600,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(50);
        canvas2.drawText(""+eventInformation.getEventPending(),PAGE_WIDTH/2,600,titlePaint);




        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Expense : ",PAGE_WIDTH/2-350,700,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(50);
        canvas2.drawText(""+eventInformation.getEventExp(),PAGE_WIDTH/2,700,titlePaint);



        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(100);
        canvas2.drawText("Vargani List",PAGE_WIDTH/2,1000,titlePaint);









        int consts=150;

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Reciept ID",0+consts,1200,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Date",PAGE_WIDTH/5*1+consts,1200,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("DatePaid",PAGE_WIDTH/5*2+consts,1200,titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Name",PAGE_WIDTH/5*3+consts,1200,titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        canvas2.drawText("Amount",PAGE_WIDTH/5*4+consts,1200,titlePaint);





        PdfDocument.PageInfo tempPage=null;
        PdfDocument.Page myTempPage= myPage2;
        Canvas tempCanvas=canvas2;


        int currentY=1300;
        int current_Page=3;


        for (VarganiReciepts v:eventInformation.getVarganiList()){

            if(currentY>=PAGE_HEIGHT-100){
                currentY=100;
                myPdfDocument.finishPage(myTempPage);
                tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
                myTempPage= myPdfDocument.startPage(tempPage);
                tempCanvas=myTempPage.getCanvas();
            }
            else{
                currentY+=50;
            }



            if (v.getDatePaid()==null){
                titlePaint.setColor(context.getResources().getColor(R.color.red));
                totalVarganiYetToCome+=v.getAmount();
            }
            else{
                titlePaint.setColor(context.getResources().getColor(android.R.color.black));
                totalVarganiCollected+=v.getAmount();

            }
            total+=v.getAmount();

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getRecieptNumber(),0+consts,currentY,titlePaint);


            String s=v.getDateCreated();
            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+s,PAGE_WIDTH/5*1+consts,currentY,titlePaint);

             s=v.getDatePaid()==null?"NOT PAID":v.getDatePaid();
            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+s,PAGE_WIDTH/5*2+consts,currentY,titlePaint);



            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getName(),PAGE_WIDTH/5*3+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getAmount(),PAGE_WIDTH/5*4+consts,currentY,titlePaint);

        }

        titlePaint.setColor(context.getResources().getColor(android.R.color.black));

        currentY+=50;
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawLine(50,currentY,PAGE_WIDTH-40,currentY,titlePaint);
        }

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));



        currentY+=50;
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawText("Vargani Collected ",PAGE_WIDTH/4*1+consts,currentY,titlePaint);
            tempCanvas.drawText(""+totalVarganiCollected,PAGE_WIDTH/4*3+consts,currentY,titlePaint);
        }
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

        currentY+=20;

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawLine(50,currentY+20,PAGE_WIDTH-50,currentY+20,titlePaint);
        }

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));


        currentY+=70;
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawText("Vargani Yet To Collect ",PAGE_WIDTH/4*1+consts,currentY,titlePaint);
            tempCanvas.drawText(""+totalVarganiYetToCome,PAGE_WIDTH/4*3+consts,currentY,titlePaint);
        }
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

        currentY+=20;

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawLine(50,currentY+20,PAGE_WIDTH-50,currentY+20,titlePaint);
        }

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));





        myPdfDocument.finishPage(myTempPage);
        tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
        myTempPage= myPdfDocument.startPage(tempPage);
        tempCanvas=myTempPage.getCanvas();



        titlePaint.setColor(context.getResources().getColor(android.R.color.black));

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(100);
        tempCanvas.drawText("Expense List",PAGE_WIDTH/2,100,titlePaint);



        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        tempCanvas.drawText("Date",0+consts,300,titlePaint);



        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        tempCanvas.drawText("Expense For",PAGE_WIDTH/4*1+consts,300,titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        tempCanvas.drawText("Given To",PAGE_WIDTH/4*2+consts,300,titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        tempCanvas.drawText("Amount",PAGE_WIDTH/4*3+consts,300,titlePaint);



        currentY=400;


        for (Expanditures v:eventInformation.getExpanditures()){

            if(currentY>=PAGE_HEIGHT-100){
                currentY=100;
                myPdfDocument.finishPage(myTempPage);
                tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
                myTempPage= myPdfDocument.startPage(tempPage);
                tempCanvas=myTempPage.getCanvas();
            }
            else{
                currentY+=50;
            }
            totalExpenseDone+=v.getAmount();



            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getDate(),0+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getGivenFor(),PAGE_WIDTH/4*1+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getGivenTo(),PAGE_WIDTH/4*2+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getAmount(),PAGE_WIDTH/4*3+consts,currentY,titlePaint);

        }


        currentY+=20;
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawLine(50,currentY,PAGE_WIDTH-50,currentY,titlePaint);
        }

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));



        currentY+=70    ;
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        if(currentY<=PAGE_HEIGHT-100){
            tempCanvas.drawText("Expense Money  ",PAGE_WIDTH/4*1+consts,currentY,titlePaint);
            tempCanvas.drawText(""+totalExpenseDone,PAGE_WIDTH/4*3+consts,currentY,titlePaint);
        }
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        myPdfDocument.finishPage(myTempPage);


        currentY=100;
        tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
        myTempPage= myPdfDocument.startPage(tempPage);
        tempCanvas=myTempPage.getCanvas();

        titlePaint.setTextSize(70);
        tempCanvas.drawText("Day Wise Vargani Collection...",PAGE_WIDTH/2,currentY,titlePaint);

        currentY+=20;
        tempCanvas.drawLine(100,currentY,PAGE_WIDTH-100,currentY,titlePaint);


        currentY+=100;





        // Day Wise Vargani Display

        Map<Date, Datad> m = new HashMap<Date, Datad>();
        SimpleDateFormat er = new SimpleDateFormat("dd/MM/yyyy");
        for (VarganiReciepts v: eventInformation.getVarganiList()) {
            try {

                Date d=er.parse(v.getDateCreated());
                ;
                if(!m.containsKey(d)){
                    Datad o=new Datad();
                    o.AmountVarganiRecieptGiven=0;
                    o.AmountVarganiRecieptGivenPaid=0;
                    o.AmountVarganiRecieptGivenUnpaid=0;
//                    o.previousVarganiCollected=0;

                    m.put( d, o);
                }
                Datad deo=m.get(d);

                deo.AmountVarganiRecieptGiven+=v.getAmount();
                if(v.getDatePaid()!=null && v.getDatePaid().equals(er.format(d))){
                    deo.AmountVarganiRecieptGivenPaid+=v.getAmount();
                }
                else if(v.getDatePaid()==null){
                    deo.AmountVarganiRecieptGivenUnpaid+=v.getAmount();
                }




                m.put( d, deo);

            } catch (Exception e) {


            }
        }
        Map<Date, Datad> m1 = new TreeMap(m);


        ArrayList<String> xAxis=new ArrayList<>();
        ArrayList<Integer> yAxis=new ArrayList<>();




        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(20);
        tempCanvas.drawText("Date",consts,currentY,titlePaint);


        consts-=30;

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(20);
        tempCanvas.drawText("Vargani Reciept of Cost",PAGE_WIDTH/5*1+consts,currentY,titlePaint);


        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(20);
        tempCanvas.drawText("Vargani Paid Today",PAGE_WIDTH/5*2+consts,currentY,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(20);
        tempCanvas.drawText("Vargani Pending Today",PAGE_WIDTH/5*3+consts,currentY,titlePaint);

//
//        titlePaint.setTextAlign(Paint.Align.CENTER);
//        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL));
//        titlePaint.setTextSize(20);
//        tempCanvas.drawText("Pending Vargani Collected",PAGE_WIDTH/5*4+consts,currentY,titlePaint);






        for (Map.Entry<Date, Datad> v : m1.entrySet())
        {
            if(currentY>=PAGE_HEIGHT-100){
                currentY=100;
                myPdfDocument.finishPage(myTempPage);
                tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
                myTempPage= myPdfDocument.startPage(tempPage);
                tempCanvas=myTempPage.getCanvas();
            }
            else{
                currentY+=70;
            }




            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+er.format(v.getKey()),consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getValue().AmountVarganiRecieptGiven,PAGE_WIDTH/5*1+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getValue().AmountVarganiRecieptGivenPaid,PAGE_WIDTH/5*2+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getValue().AmountVarganiRecieptGivenUnpaid,PAGE_WIDTH/5*3+consts,currentY,titlePaint);


//            titlePaint.setTextAlign(Paint.Align.CENTER);
//            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
//            titlePaint.setTextSize(30);
//            tempCanvas.drawText(""+v.getValue().previousVarganiCollected,PAGE_WIDTH/5*4+consts,currentY,titlePaint);





        }

        // Day Wise Expanditure Display

        HashMap<Date, Integer> df = new HashMap<Date, Integer>();
        er = new SimpleDateFormat("dd/MM/yyyy");
        for (Expanditures v: eventInformation.getExpanditures()) {
            try {

                Date d=er.parse(v.getDate());

                if(!df.containsKey(d)){
                    df.put( d, 0);
                }
                df.put( d, df.get(d) + v.getAmount());

            } catch (Exception e) {


            }
        }
        m1 = new TreeMap(df);


        currentY+=200;

        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(70);
        tempCanvas.drawText("Day Wise Expanditure",PAGE_WIDTH/2,currentY,titlePaint);

        currentY+=20;
        tempCanvas.drawLine(100,currentY,PAGE_WIDTH-100,currentY,titlePaint);


        currentY+=100;


        if(currentY>=PAGE_HEIGHT-200){
            currentY=100;
            myPdfDocument.finishPage(myTempPage);
            tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
            myTempPage= myPdfDocument.startPage(tempPage);
            tempCanvas=myTempPage.getCanvas();
        }





        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        tempCanvas.drawText(" Date",PAGE_WIDTH/4*1+consts,currentY,titlePaint);



        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(40);
        tempCanvas.drawText(" Amount ",PAGE_WIDTH/4*2+consts,currentY,titlePaint);




        for (Map.Entry<Date, Integer> v : df.entrySet())
        {
            if(currentY>=PAGE_HEIGHT-100){
                currentY=100;
                myPdfDocument.finishPage(myTempPage);
                tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
                myTempPage= myPdfDocument.startPage(tempPage);
                tempCanvas=myTempPage.getCanvas();
            }
            else{
                currentY+=70;
            }



            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+er.format(v.getKey()),PAGE_WIDTH/4*1+consts,currentY,titlePaint);


            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            titlePaint.setTextSize(30);
            tempCanvas.drawText(""+v.getValue(),PAGE_WIDTH/4*2+consts,currentY,titlePaint);

        }


        currentY+=200;

        if(currentY>=PAGE_HEIGHT-200){
            currentY=100;
            myPdfDocument.finishPage(myTempPage);
            tempPage=new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,current_Page++).create();
            myTempPage= myPdfDocument.startPage(tempPage);
            tempCanvas=myTempPage.getCanvas();
        }

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        titlePaint.setTextSize(50);
        tempCanvas.drawText("Total Cost Of Reciepts",PAGE_WIDTH/2-200,currentY,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        titlePaint.setTextSize(50);
        tempCanvas.drawText(""+total,PAGE_WIDTH/2+200,currentY,titlePaint);









        myPdfDocument.finishPage(myTempPage);









        try{

            myPdfDocument.writeTo(outputStream);

            //Toast.makeText(context,"Completed.1515..",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(context,"Error...", Toast.LENGTH_SHORT).show();
        }

        myPdfDocument.close();
        System.out.println("COmpleted");
        Toast.makeText(context,"PDF Created Successfully...", Toast.LENGTH_SHORT).show();



        //Open PDF


        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(uri, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try{

            context.startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }






}
