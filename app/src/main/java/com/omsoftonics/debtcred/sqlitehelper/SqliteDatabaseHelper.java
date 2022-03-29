package com.omsoftonics.debtcred.sqlitehelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import com.omsoftonics.debtcred.model.EventInformation;
import com.omsoftonics.debtcred.model.Expanditures;
import com.omsoftonics.debtcred.model.VarganiReciepts;

import java.util.ArrayList;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import static com.omsoftonics.debtcred.MainActivity.EXPENSE_RECIEPTS;
import static com.omsoftonics.debtcred.MainActivity.VARGANI_RECIEPTS;

public class SqliteDatabaseHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="VARGANI_THE_APPLICATION";
    private static final String EVENT_INFORMATION_TABLE="EVENT_INFORMATION";
    private static final String VARGANI_COLLECTION_TABLE="VARGANI_COLLECTION";
    private static final String EXPENSE_COLLECTION="EXPENSE_COLLECTION";






    private static final String EVENT_INFORMATION_TABLE_EVENT_ID="EVENT_ID";
    private static final String EVENT_INFORMATION_TABLE_EVENT_NAME="EVENT_NAME";
    private static final String EVENT_INFORMATION_TABLE_EVENT_COLLECTION="EVENT_MONEY_COLLECTION";
    private static final String EVENT_INFORMATION_TABLE_EVENT_EXPANDITURE="EVENT_EXPANDITURE";
    private static final String EVENT_INFORMATION_TABLE_EVENT_PENDING_MONEY="EVENT_PENDING_MONEY";
    private static final String EVENT_INFORMATION_TABLE_EVENT_DATE_CREATED="EVENT_DATE_CREATED";




    private static final String VARGANI_COLLECTION_TABLE_VARGANI_ID="ID";
    private static final String VARGANI_COLLECTION_TABLE_EVENT_ID="EVENT_ID";
    private static final String VARGANI_COLLECTION_TABLE_RECIEPT_ID="RECIEPT_ID";
    private static final String VARGANI_COLLECTION_TABLE_RECIEPT_NAME="RECIEPT_NAME";
    private static final String VARGANI_COLLECTION_TABLE_DATE="DATE";
    private static final String VARGANI_COLLECTION_TABLE_DATE_PAID="DATE_PAID";
    private static final String VARGANI_COLLECTION_TABLE_AMOUNT="AMOUNT";


    private static final String EXPENSE_COLLECTION_TABLE_ID="ID";
    private static final String EXPENSE_COLLECTION_EVENT_ID="EVENT_ID";
    private static final String EXPENSE_COLLECTION_EXPENSE_FOR="EXPENSE_FOR";
    private static final String EXPENSE_COLLECTION_EXPENSE_BY="EXPENSE_BY";
    private static final String EXPENSE_COLLECTION_DATE="DATE";
    private static final String EXPENSE_COLLECTION_AMOUNT="AMOUNT";


    private static final String QUERY_TABLE_EVENT_INFORMATION="CREATE TABLE "+EVENT_INFORMATION_TABLE+" (\n" +
            EVENT_INFORMATION_TABLE_EVENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            EVENT_INFORMATION_TABLE_EVENT_NAME+" VARCHAR(50) ,\n" +
            EVENT_INFORMATION_TABLE_EVENT_COLLECTION+" INT ,\n" +
            EVENT_INFORMATION_TABLE_EVENT_EXPANDITURE+" INT ,\n" +
            EVENT_INFORMATION_TABLE_EVENT_PENDING_MONEY+" INT ,\n" +
            EVENT_INFORMATION_TABLE_EVENT_DATE_CREATED+" VARCHAR(45))";


    private static final String QUERY_TABLE_VARGANI_COLLECTION="CREATE TABLE "+VARGANI_COLLECTION_TABLE+" (\n" +
            VARGANI_COLLECTION_TABLE_VARGANI_ID+"   INTEGER PRIMARY KEY AUTOINCREMENT   ,\n" +
            VARGANI_COLLECTION_TABLE_EVENT_ID+"   INT ,\n" +
            VARGANI_COLLECTION_TABLE_RECIEPT_ID+"   INT ,\n" +
            VARGANI_COLLECTION_TABLE_RECIEPT_NAME+"   VARCHAR(100) ,\n" +
            VARGANI_COLLECTION_TABLE_DATE+"  VARCHAR(45) ,\n" +
            VARGANI_COLLECTION_TABLE_DATE_PAID+"   VARCHAR(45) ,\n" +
            VARGANI_COLLECTION_TABLE_AMOUNT+"   INT )";


    private static final String QUERY_TABLE_EXPENSE_COLLECTION="CREATE TABLE "+EXPENSE_COLLECTION+ "(\n" +
            EXPENSE_COLLECTION_TABLE_ID+"   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            EXPENSE_COLLECTION_EVENT_ID+"   INT ,\n" +
            EXPENSE_COLLECTION_EXPENSE_FOR+"   VARCHAR(100) ,\n" +
            EXPENSE_COLLECTION_EXPENSE_BY+"   VARCHAR(100) ,\n" +
            EXPENSE_COLLECTION_DATE+"   VARCHAR(45) ,\n" +
            EXPENSE_COLLECTION_AMOUNT+ "  INT )";






    private static final String DROP_IF_EXIST="DROP TABLE IF EXISTS ";

    SQLiteDatabase database;

    public SqliteDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SqliteDatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public SqliteDatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,1);
        database=this.getWritableDatabase();
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_TABLE_EVENT_INFORMATION);
        db.execSQL(QUERY_TABLE_EXPENSE_COLLECTION);
        db.execSQL(QUERY_TABLE_VARGANI_COLLECTION);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_IF_EXIST+EVENT_INFORMATION_TABLE);
        db.execSQL(DROP_IF_EXIST+VARGANI_COLLECTION_TABLE);
        db.execSQL(DROP_IF_EXIST+EXPENSE_COLLECTION);

        onCreate(db);
    }


    public long InsertEventInformationRecord(EventInformation info) {
        database=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(EVENT_INFORMATION_TABLE_EVENT_NAME,info.geteventName());
        values.put(EVENT_INFORMATION_TABLE_EVENT_COLLECTION,info.getEventIncome());
        values.put(EVENT_INFORMATION_TABLE_EVENT_EXPANDITURE,info.getEventExp());
        values.put(EVENT_INFORMATION_TABLE_EVENT_PENDING_MONEY,info.getEventPending());
        values.put(EVENT_INFORMATION_TABLE_EVENT_DATE_CREATED,info.getDateCreated());



        return database.insert(EVENT_INFORMATION_TABLE,null,values);
    }

    // INSERT INTO EVENT_INFORMATION ( EVENT_NAME, EVENT_MONEY_COLLECTION, EVENT_EXPANDITURE, EVENT_PENDING_MONEY, EVENT_DATE_CREATED) VALUES ( nhyyt, 0, 0,0, 07/12/2021)
    public long UpdateEventInformationRecord(EventInformation info) {
        database=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(EVENT_INFORMATION_TABLE_EVENT_NAME,info.geteventName());
        values.put(EVENT_INFORMATION_TABLE_EVENT_COLLECTION,info.getEventIncome());
        values.put(EVENT_INFORMATION_TABLE_EVENT_EXPANDITURE,info.getEventExp());
        values.put(EVENT_INFORMATION_TABLE_EVENT_PENDING_MONEY,info.getEventPending());
        values.put(EVENT_INFORMATION_TABLE_EVENT_DATE_CREATED,info.getDateCreated());



        return database.update(EVENT_INFORMATION_TABLE,values,EVENT_INFORMATION_TABLE_EVENT_ID+"= ?", new String[]{Integer.toString(info.getEventID())});

    }

    public long RemoveRecord(EventInformation info) {
        database=this.getWritableDatabase();
        return database.delete(EVENT_INFORMATION_TABLE,EVENT_INFORMATION_TABLE_EVENT_ID+"= ?", new String[]{Integer.toString(info.getEventID())});
    }


    public ArrayList<EventInformation> GetAllEvents(){
        database=this.getReadableDatabase();

        String query="SELECT * FROM "+EVENT_INFORMATION_TABLE;
        ArrayList<EventInformation> informations=new ArrayList<>();


        Cursor cursor=database.rawQuery(query,null);
       // cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            do {
                EventInformation info = new EventInformation();

                info.setDateCreated(cursor.getString(cursor.getColumnIndex(EVENT_INFORMATION_TABLE_EVENT_DATE_CREATED)));
                info.setEventID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EVENT_INFORMATION_TABLE_EVENT_ID))));
                info.setEventIncome(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EVENT_INFORMATION_TABLE_EVENT_COLLECTION))));
                info.seteventName(cursor.getString(cursor.getColumnIndex(EVENT_INFORMATION_TABLE_EVENT_NAME)));
                info.setEventPending(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EVENT_INFORMATION_TABLE_EVENT_PENDING_MONEY))));
                info.setEventExp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EVENT_INFORMATION_TABLE_EVENT_EXPANDITURE))));
                ArrayList<VarganiReciepts> v = VARGANI_RECIEPTS.stream().filter(p -> p.getEventID() == info.getEventID()).collect(Collectors.toCollection(ArrayList::new));
                for (int i=0;i<v.size();i++){
                    v.get(i).setIndex(i);
                }
                info.setVarganiList(v);

                ArrayList<Expanditures> x=EXPENSE_RECIEPTS.stream().filter(p -> p.getEventID() == info.getEventID()).collect(Collectors.toCollection(ArrayList::new));
                for (int i=0;i<x.size();i++) {
                    x.get(i).setIndex(i);
                }
                info.setExpanditures(x);
                informations.add(0, info);

            } while (cursor.moveToNext());
        }
        return informations;

    }

    public ArrayList<VarganiReciepts> GetAllVarganiReciepts() {
        database=this.getReadableDatabase();

        String query="SELECT * FROM "+VARGANI_COLLECTION_TABLE;
        ArrayList<VarganiReciepts> informations=new ArrayList<>();


        Cursor cursor=database.rawQuery(query,null);
        // cursor.moveToFirst();
        if(cursor.moveToFirst()) {
            do {
                VarganiReciepts info = new VarganiReciepts();

                info.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_VARGANI_ID))));
                info.setEventID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_EVENT_ID))));
                info.setRecieptNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_RECIEPT_ID))));
                info.setName(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_RECIEPT_NAME)));
                info.setDateCreated(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_DATE)));
                info.setDatePaid(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_DATE_PAID)));
                info.setAmount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_AMOUNT))));
                info.setIndex(informations.size());


                informations.add(info);

            } while (cursor.moveToNext());
        }

        return informations;
    }

    public ArrayList<VarganiReciepts> GetSpecificVarganis(String s) {
        database=this.getReadableDatabase();

        String query="SELECT * FROM "+VARGANI_COLLECTION_TABLE+" WHERE "+VARGANI_COLLECTION_TABLE_RECIEPT_NAME+" LIKE "+" '%"+s+"%' OR "+VARGANI_COLLECTION_TABLE_RECIEPT_ID+" LIKE "+" '%"+s+"%'";
        ArrayList<VarganiReciepts> informations=new ArrayList<>();


        Cursor cursor=database.rawQuery(query,null);
        // cursor.moveToFirst();
        if(cursor.moveToFirst()) {
            do {
                VarganiReciepts info = new VarganiReciepts();

                info.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_VARGANI_ID))));
                info.setEventID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_EVENT_ID))));
                info.setRecieptNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_RECIEPT_ID))));
                info.setName(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_RECIEPT_NAME)));
                info.setDateCreated(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_DATE)));
                info.setDatePaid(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_DATE_PAID)));
                info.setAmount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VARGANI_COLLECTION_TABLE_AMOUNT))));
                info.setIndex(informations.size());


                informations.add(info);

            } while (cursor.moveToNext());
        }

        return informations;
    }

    public ArrayList<Expanditures> GETAllExpanditures() {
        database=this.getReadableDatabase();

        String query="SELECT * FROM "+EXPENSE_COLLECTION;
        ArrayList<Expanditures> informations=new ArrayList<>();


        Cursor cursor=database.rawQuery(query,null);
        //cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            do {
                Expanditures info = new Expanditures();
                info.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSE_COLLECTION_TABLE_ID))));
                info.setGivenFor(cursor.getString(cursor.getColumnIndex(EXPENSE_COLLECTION_EXPENSE_FOR)));
                info.setGivenTo(cursor.getString(cursor.getColumnIndex(EXPENSE_COLLECTION_EXPENSE_BY)));
                info.setDate(cursor.getString(cursor.getColumnIndex(EXPENSE_COLLECTION_DATE)));
                info.setAmount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSE_COLLECTION_AMOUNT))));
                info.setEventID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSE_COLLECTION_EVENT_ID))));
                info.setIndex(informations.size());

                informations.add(info);

            } while (cursor.moveToNext());
        }
        return informations;
    }

    public long InsertExpanditure(Expanditures info) {

        database=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(EXPENSE_COLLECTION_AMOUNT,info.getAmount());
        values.put(EXPENSE_COLLECTION_DATE,info.getDate());
        values.put(EXPENSE_COLLECTION_EVENT_ID,info.getEventID());
        values.put(EXPENSE_COLLECTION_EXPENSE_BY,info.getGivenTo());
        values.put(EXPENSE_COLLECTION_EXPENSE_FOR,info.getGivenFor());

        return database.insert(EXPENSE_COLLECTION,null,values);

    }

    public long UpdateExpanditure(Expanditures info) {
        database=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(EXPENSE_COLLECTION_AMOUNT,info.getAmount());
        values.put(EXPENSE_COLLECTION_DATE,info.getDate());
        values.put(EXPENSE_COLLECTION_EVENT_ID,info.getEventID());
        values.put(EXPENSE_COLLECTION_EXPENSE_BY,info.getGivenTo());
        values.put(EXPENSE_COLLECTION_EXPENSE_FOR,info.getGivenFor());
        return database.update(EXPENSE_COLLECTION,values,EXPENSE_COLLECTION_TABLE_ID+"= ?", new String[]{Integer.toString(info.getId())});
    }

    public long RemoveExpanditure(Expanditures info) {
        database=this.getWritableDatabase();
        return database.delete(EXPENSE_COLLECTION,EXPENSE_COLLECTION_TABLE_ID+"= ?", new String[]{Integer.toString(info.getId())});
    }

    public long InsertVargani(VarganiReciepts info) {

        database=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(VARGANI_COLLECTION_TABLE_AMOUNT,info.getAmount());
        values.put(VARGANI_COLLECTION_TABLE_DATE,info.getDateCreated());
        values.put(VARGANI_COLLECTION_TABLE_DATE_PAID,info.getDatePaid());
        values.put(VARGANI_COLLECTION_TABLE_EVENT_ID,info.getEventID());
        values.put(VARGANI_COLLECTION_TABLE_RECIEPT_NAME,info.getName());
        values.put(VARGANI_COLLECTION_TABLE_RECIEPT_ID,info.getRecieptNumber());


        return database.insert(VARGANI_COLLECTION_TABLE,null,values);

    }

    public long UpdateVargani(VarganiReciepts info) {

        database=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(VARGANI_COLLECTION_TABLE_AMOUNT,info.getAmount());
        values.put(VARGANI_COLLECTION_TABLE_DATE,info.getDateCreated());
        values.put(VARGANI_COLLECTION_TABLE_DATE_PAID,info.getDatePaid());
        values.put(VARGANI_COLLECTION_TABLE_EVENT_ID,info.getEventID());
        values.put(VARGANI_COLLECTION_TABLE_RECIEPT_NAME,info.getName());
        values.put(VARGANI_COLLECTION_TABLE_RECIEPT_ID,info.getRecieptNumber());


        return database.update(VARGANI_COLLECTION_TABLE,values,VARGANI_COLLECTION_TABLE_RECIEPT_ID+"= ?", new String[]{Integer.toString(info.getRecieptNumber())});
    }

    public long RemoveVargani(VarganiReciepts info) {
        database=this.getWritableDatabase();
        return database.delete(VARGANI_COLLECTION_TABLE,VARGANI_COLLECTION_TABLE_VARGANI_ID+"= ?", new String[]{Integer.toString(info.getId())});
    }

    public void RemoveRecordsforEvent(int eventID) {

        getWritableDatabase().delete(VARGANI_COLLECTION_TABLE,VARGANI_COLLECTION_TABLE_EVENT_ID+"= ?", new String[]{Integer.toString(eventID)});
        getWritableDatabase().delete(EXPENSE_COLLECTION,EXPENSE_COLLECTION_EVENT_ID+"= ?", new String[]{Integer.toString(eventID)});

    }

    public void ExportData(Activity context) {
        Toast.makeText(context,"Import and Export Functionality will be available soon...", Toast.LENGTH_SHORT).show();

        //+"VarganiBackup_" + GetCurrentdate().replace('/', '_') + ".txt"

//        boolean success = true;
//        if (!BACKUP_IMPORT_EXPORT.exists())
//            success = BACKUP_IMPORT_EXPORT.mkdirs();
//        if (success) {
//            try {
//                ArrayList<VarganiReciepts> vargani=GetAllVarganiReciepts();
//                ArrayList<Expanditures> expanditures=GETAllExpanditures();
//                ArrayList<EventInformation> events=GetAllEvents();
//
//
//
//                FileWriter fileWriter=new FileWriter(BACKUP_IMPORT_EXPORT);
//
//
//                fileWriter.write("TABLE 1 STARTED\n");
//                for (EventInformation e:events){
//                    fileWriter.append(""+e);
//                    fileWriter.append("\n");
//                }
//
//
//
//                fileWriter.write("\nTABLE 2 STARTED\n");
//                for (VarganiReciepts e:vargani){
//                    fileWriter.append(""+e);
//                    fileWriter.append("\n");
//                }
//
//
//
//                fileWriter.write("\nTABLE 3 STARTED\n");
//                for (Expanditures e:expanditures){
//                    fileWriter.append(""+e);
//                    fileWriter.append("\n");
//                }
//
//
//
//                fileWriter.flush();
//                fileWriter.close();
//
//                Toast.makeText(context,"Exported...",Toast.LENGTH_SHORT).show();
//
//
//            }
//            catch (Exception e){
//                Toast.makeText(context, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }


    }


    public void ImportData(Activity context) {

        Toast.makeText(context,"Import and Export Functionality will be available soon...", Toast.LENGTH_SHORT).show();

//
//        File csvFile=BACKUP_IMPORT_EXPORT;
//
//
//        if(!csvFile.exists()){
//            Toast.makeText(context,"File Doesn't Exist...",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        int current=0;
//        try{
//            CSVReader csvReader=new CSVReader(new FileReader(csvFile.getAbsolutePath()));
//            String[] nextLine;
//
//
//            while ((nextLine=csvReader.readNext())!=null){
//                if(!nextLine.equals("")) {
//                    if (nextLine.equals("TABLE 1 STARTED")) {
//                        current = 0;
//                    } else if (nextLine.equals("TABLE 2 STARTED")) {
//                        current = 1;
//                    } else if (nextLine.equals("TABLE 3 STARTED")) {
//                        current = 2;
//                    }
//
//
//
//
//                    if(current==0){
//
//                        EventInformation event=new EventInformation();
//                        event.setDateCreated(nextLine[0]);
//                        event.seteventName(nextLine[1]);
//                        event.setEventIncome(Integer.parseInt(nextLine[2]));
//                        event.setEventExp(Integer.parseInt(nextLine[3]));
//                        event.setEventPending(Integer.parseInt(nextLine[4]));
//                        event.SaveRecord(context);
//
//                    }
//                    else if(current==1){
//
//                    }
//                    else if(current==2){
//
//                    }
//
//                }
//            }
//        }
//        catch (Exception e){
//
//        }
//
//

    }
}


