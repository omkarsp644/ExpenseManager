package com.omsoftonics.debtcred.model;

import android.content.Context;
import android.widget.Toast;

import com.omsoftonics.debtcred.sqlitehelper.SqliteDatabaseHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.omsoftonics.debtcred.MainActivity.EXPENSE_RECIEPTS;
import static com.omsoftonics.debtcred.MainActivity.VARGANI_RECIEPTS;
import static com.omsoftonics.debtcred.MainActivity.currentEventInformation;

public class EventInformation {
    public String dateCreated;
    public String eventName;
    public int eventID;
    public int eventIncome;
    public int eventExp;
    public int eventPending;
    public int index;

    public ArrayList<VarganiReciepts> varganiList=new ArrayList<>();
    public ArrayList<Expanditures> expanditures=new ArrayList<>();


    @Override
    public String toString() {
        return
                dateCreated + ',' +
                 eventName + ',' +
                eventIncome +',' +
                 eventExp +',' +
                 eventPending;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getEventPending() {
        return eventPending;
    }

    public void setEventPending(int eventPending) {
        this.eventPending = eventPending;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String geteventName() {
        return eventName;
    }

    public void seteventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getEventIncome() {
        return eventIncome;
    }

    public void setEventIncome(int eventIncome) {
        this.eventIncome = eventIncome;
    }

    public int getEventExp() {
        return eventExp;
    }

    public void setEventExp(int eventExp) {
        this.eventExp = eventExp;
    }

    public ArrayList<VarganiReciepts> getVarganiList() {
        return varganiList;
    }

    public void setVarganiList(ArrayList<VarganiReciepts> varganiList) {
        this.varganiList = varganiList;
    }

    public ArrayList<Expanditures> getExpanditures() {
        return expanditures;
    }

    public void setExpanditures(ArrayList<Expanditures> expanditures) {
        this.expanditures = expanditures;
    }

    public void AddExpanditures(Expanditures data) {
        this.expanditures.add(0,data);
        // add login to reduce Money
    }

    public void AddVargani(VarganiReciepts data) {
        if(this.varganiList==null){
            this.varganiList=new ArrayList<>();
        }
        data.setIndex(this.varganiList.size());
        this.varganiList.add(data);
        if(data.getDatePaid()!=null)
            this.eventIncome+=data.getAmount();
        else{
            this.eventPending+=data.getAmount();
        }
    }

    public void UpdateVargani(VarganiReciepts data) {
        VarganiReciepts rec= (VarganiReciepts) this.varganiList.stream().filter(p->p.getRecieptNumber()==data.getRecieptNumber()).findFirst().orElse(null);

        this.varganiList.set(rec.getIndex(),data);
    }

    public void UpdateCashInfo(VarganiReciepts data) {

        VarganiReciepts originalData=this.varganiList.stream().filter(p->p.getRecieptNumber()==data.getRecieptNumber()).findFirst().orElse(null);

        if((originalData.getDatePaid()==null && data.getDatePaid()==null) || (originalData.getDatePaid()!=null && data.getDatePaid()!=null)){
            return ;
        }
        else {
            if (data.getDatePaid() != null) {
                this.eventIncome += data.getAmount();
                this.eventPending -= data.getAmount();
            } else {
                this.eventPending += data.getAmount();
                this.eventIncome -= data.getAmount();
            }
        }
        //this.varganiList.set(data.getIndex(),data);
    }


    public void DeleteExpenseRecord(Expanditures exp){

        this.eventExp-=exp.getAmount();
        this.eventIncome+=exp.getAmount();
        this.expanditures.remove(exp.getIndex());
    }




    public EventInformation(String dateCreated, String eventName, int eventID, int eventIncome, int eventExp) {
        this.dateCreated = dateCreated;
        this.eventName = eventName;
        this.eventID = eventID;
        this.eventIncome = eventIncome;
        this.eventExp = eventExp;
    }

    public EventInformation() {
    }

    public EventInformation(String eventName) {
        this.eventName = eventName;
        this.dateCreated =GetCurrentdate();
        this.varganiList=new ArrayList<>();
        this.expanditures=new ArrayList<>();
        this.eventIncome=0;
        this.eventExp=0;
    }


        public static String GetCurrentdate(){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(new Date(System.currentTimeMillis()));
        }

    public void checkChangeInCash(VarganiReciepts reciepts) {

        VarganiReciepts originalData=this.varganiList.stream().filter(p->p.getRecieptNumber()==reciepts.getRecieptNumber()).findFirst().orElse(null);


        if(reciepts.getAmount()!=originalData.getAmount()){
            if (reciepts.getDatePaid()==null){
                this.eventPending-=originalData.getAmount();
                this.eventPending+=reciepts.getAmount();
            }
            else{
                this.eventIncome-=originalData.getAmount();
                this.eventIncome+=reciepts.getAmount();
            }
        }
    }

    public void AddExpense(Expanditures e) {
        if(expanditures==null){
            expanditures=new ArrayList<>();
        }
        eventIncome-=e.getAmount();
        eventExp+=e.getAmount();
        expanditures.add(e);
    }

    public void UpdateExpense(Expanditures e) {
        if(expanditures.get(e.getId()).getAmount()!=e.getAmount()){
            eventIncome+=expanditures.get(e.getId()).getAmount();
            eventIncome-=e.getAmount();


            eventExp-=expanditures.get(e.getId()).getAmount();
            eventExp+=e.getAmount();

        }
        expanditures.set(e.getId(),e);
    }

    public boolean SaveRecord(Context context){

        SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
        long result=databaseHelper.InsertEventInformationRecord(this);
        if (result!=-1){
            Toast.makeText(context,"Event Registered Successfully....", Toast.LENGTH_SHORT).show();
            this.setEventID((int)result);
            return true;
        }
        else{
            Toast.makeText(context,"Event Not Registered ....", Toast.LENGTH_SHORT).show();

            return false;

        }
    }


    public boolean UpdateRecord(Context context){

        SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
        long result=databaseHelper.UpdateEventInformationRecord(this);
        if (result!=-1){
            //Toast.makeText(context,"Event Information Updated Successfully....",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            //Toast.makeText(context,"Event Not Updated ....",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void RemoveRecord(Context context){



        SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);


        Toast.makeText(context,"Event Information Removed Successfully....", Toast.LENGTH_SHORT).show();
        databaseHelper.RemoveRecordsforEvent(this.eventID);
        VARGANI_RECIEPTS=VARGANI_RECIEPTS.stream().filter(p->p.getEventID()!=this.eventID).collect(Collectors.toCollection(ArrayList::new));
        EXPENSE_RECIEPTS=EXPENSE_RECIEPTS.stream().filter(p->p.getEventID()!=this.eventID).collect(Collectors.toCollection(ArrayList::new));

        currentEventInformation.setExpanditures(new ArrayList<>());
        currentEventInformation.setVarganiList(new ArrayList<>());
        currentEventInformation.setEventExp(0);
        currentEventInformation.setEventPending(0);
        currentEventInformation.setEventIncome(0);
        currentEventInformation.UpdateRecord(context);

    }

    public boolean DeleteRecord(Context context){
        SqliteDatabaseHelper helper=new SqliteDatabaseHelper(context);
        long result=helper.RemoveRecord(this);
        if (result!=-1){
            Toast.makeText(context,"Event Information Deleted Successfully....", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(context,"Event Not Deleted ....", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void DeleteVarganiRecord(VarganiReciepts varganiReciepts) {

        VarganiReciepts originalData=this.varganiList.stream().filter(p->p.getRecieptNumber()==varganiReciepts.getRecieptNumber()).findFirst().orElse(null);

        if(varganiReciepts.getDatePaid()==null){
            this.eventPending-=varganiReciepts.getAmount();
        }
        else if(varganiReciepts.getDatePaid()!=null){
            this.eventIncome-=varganiReciepts.getAmount();
        }

        this.varganiList.remove(originalData.getIndex());
    }
}
