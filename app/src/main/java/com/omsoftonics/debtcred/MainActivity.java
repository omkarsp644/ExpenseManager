package com.omsoftonics.debtcred;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.omsoftonics.debtcred.model.EventInformation;
import com.omsoftonics.debtcred.model.Expanditures;
import com.omsoftonics.debtcred.model.VarganiReciepts;
import com.omsoftonics.debtcred.sqlitehelper.SqliteDatabaseHelper;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //https://app.flycricket.com/app/ad35d8e385cb4bd8b42f3982901f5af0/screenshots

    public static EventInformation currentEventInformation=null;
    public static ArrayList<EventInformation> currentEvents=new ArrayList<>();

    public static ArrayList<VarganiReciepts> VARGANI_RECIEPTS=new ArrayList<>();
    public static ArrayList<Expanditures> EXPENSE_RECIEPTS  =new ArrayList<>();


    public static File BACKUP_IMPORT_EXPORT = new File(Environment.getExternalStorageDirectory() + File.separator + "VarganiBackup/VarganiBackupfile.csv");


    public static int REQUEST_CODE_PERMISSIONS=234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqliteDatabaseHelper helper=new SqliteDatabaseHelper(this);

        VARGANI_RECIEPTS=helper.GetAllVarganiReciepts();
        EXPENSE_RECIEPTS=helper.GETAllExpanditures();
        currentEvents=helper.GetAllEvents();

        new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(com.omsoftonics.debtcred.MainActivity.this, com.omsoftonics.debtcred.EventsDashboard.class));
                    finish();
                }
            }
        }.start();





//        addEvents.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    for (DataSnapshot buff : snapshot.getChildren()) {
//                        EventInformation info = buff.getValue(EventInformation.class);
//                        currentEvents.add(info);
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}