package com.omsoftonics.debtcred.model;

import android.content.Context;
import android.widget.Toast;

import com.omsoftonics.debtcred.R;
import com.omsoftonics.debtcred.sqlitehelper.SqliteDatabaseHelper;

public class Expanditures {
    int id;
    String date,givenTo,givenFor;
    int amount;
    int EventID;
    int index;

    @Override
    public String toString() {
        return date + ',' +
                givenTo + ',' +
                givenFor + ',' +
                amount +',' +
               EventID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int eventID) {
        EventID = eventID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGivenTo() {
        return givenTo;
    }

    public void setGivenTo(String givenTo) {
        this.givenTo = givenTo;
    }

    public String getGivenFor() {
        return givenFor;
    }

    public void setGivenFor(String givenFor) {
        this.givenFor = givenFor;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Expanditures(int id, String date, String givenTo, String givenFor, int amount) {
        this.id = id;
        this.date = date;
        this.givenTo = givenTo;
        this.givenFor = givenFor;
        this.amount = amount;
    }

    public Expanditures() {
    }


    public boolean SaveRecord(Context context){

        SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
        long result=databaseHelper.InsertExpanditure(this);
        if (result!=-1){
            Toast.makeText(context, context.getResources().getString(R.string.successfully_registered), Toast.LENGTH_SHORT).show();
            this.setId((int)result);
            return true;
        }
        else{
            Toast.makeText(context, context.getResources().getString(R.string.event_not_registered), Toast.LENGTH_SHORT).show();

            return false;

        }
    }


    public boolean UpdateRecord(Context context){

        SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
        long result=databaseHelper.UpdateExpanditure(this);
        if (result!=-1){
           // Toast.makeText(context,"Event Information Updated Successfully....",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            //Toast.makeText(context,"Event Not Updated ....",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean RemoveRecord(Context context){

        SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
        long result=databaseHelper.RemoveExpanditure(this);
        if (result!=-1){
            Toast.makeText(context, context.getResources().getString(R.string.event_removed_success), Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(context, context.getResources().getString(R.string.event_not_registred), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
