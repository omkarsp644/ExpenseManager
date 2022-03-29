    package com.omsoftonics.debtcred.model;

import android.content.Context;
import android.widget.Toast;

import com.omsoftonics.debtcred.sqlitehelper.SqliteDatabaseHelper;

    public class VarganiReciepts {
        public String dateCreated;
        public int amount;
        public String name;
        public int recieptNumber;
        public String datePaid;
        public int index;
        public int EventID;
        public int id;


        @Override
        public String toString() {
            return
                    dateCreated + ',' +
                     amount +',' +
                    name + ',' +
                     recieptNumber +',' +
                     datePaid + ',' +
                     EventID;
        }

        public int getIndex() {
            return index;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setEventID(int eventID) {
            EventID = eventID;
        }

        public int getEventID() {
            return EventID;
        }


        public void setIndex(int index) {
            this.index = index;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRecieptNumber() {
            return recieptNumber;
        }

        public void setRecieptNumber(int recieptNumber) {
            this.recieptNumber = recieptNumber;
        }

        public String getDatePaid() {
            return datePaid;
        }

        public void setDatePaid(String datePaid) {
            this.datePaid = datePaid;
        }


        public VarganiReciepts() {
        }

        public VarganiReciepts(String dateCreated, int amount, String name, int recieptNumber, String datePaid) {
            this.dateCreated = dateCreated;
            this.amount = amount;
            this.name = name;
            this.recieptNumber = recieptNumber;
            this.datePaid = datePaid;
        }

        public boolean SaveRecord(Context context){

            SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
            long result=databaseHelper.InsertVargani(this);
            if (result!=-1){
                Toast.makeText(context,"VARGANI Successfully Registered....", Toast.LENGTH_SHORT).show();
                this.setId((int)result);
                return true;
            }
            else{
                Toast.makeText(context,"VARGANI Not Registered ....", Toast.LENGTH_SHORT).show();

                return false;

            }
        }


        public boolean UpdateRecord(Context context){

            SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
            long result=databaseHelper.UpdateVargani(this);
            if (result!=-1){
                //Toast.makeText(context,"Event Information Updated Successfully....",Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                //Toast.makeText(context,"Event Not Updated ....",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        public boolean RemoveRecord(Context context){

            SqliteDatabaseHelper databaseHelper=new SqliteDatabaseHelper(context);
            long result=databaseHelper.RemoveVargani(this);
            if (result!=-1){
                Toast.makeText(context,"Event Information Removed Successfully....", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                Toast.makeText(context,"Event Not Removed ....", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


    }
