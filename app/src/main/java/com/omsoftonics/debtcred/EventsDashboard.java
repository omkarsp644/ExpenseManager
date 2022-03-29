package com.omsoftonics.debtcred;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.omsoftonics.debtcred.adapter.EventsAdapter;
import com.omsoftonics.debtcred.model.EventInformation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.omsoftonics.debtcred.MainActivity.REQUEST_CODE_PERMISSIONS;
import static com.omsoftonics.debtcred.MainActivity.currentEventInformation;
import static com.omsoftonics.debtcred.MainActivity.currentEvents;

public class EventsDashboard extends AppCompatActivity {

    BottomSheetDialog dialog;
    CardView openDialog;
    EditText eventName;
    CardView registerEvent;
    RecyclerView displayEvents;
    public static EventsAdapter eventsAdapter;
    DrawerLayout drawerView;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_dashboard);




        dialog= new BottomSheetDialog(com.omsoftonics.debtcred.EventsDashboard.this);
        dialog.setContentView(R.layout.bottom_sheet_dialog);
        dialog.setCanceledOnTouchOutside(true);
        drawerView=(DrawerLayout) findViewById(R.id.varganiLayout);

        toolbar=(Toolbar) findViewById(R.id.Toolbar);
        navigationView=(NavigationView)findViewById(R.id.navigationView);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerView.openDrawer(navigationView);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.rateApp:
                        RateMyApp();
                        break;
                }
                return true;
            }

        });
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                SqliteDatabaseHelper helper=new SqliteDatabaseHelper(getApplicationContext());
//                switch (item.getItemId()){
//                    case R.id.exportItem:
//
//                        if(Permission.verifyStoragePermissions(EventsDashboard.this))
//                            helper.ExportData(EventsDashboard.this);
//                        else
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.permission_required),Toast.LENGTH_SHORT).
//                                    show();
//                        break;
//                    case R.id.importItem:
//                        if(Permission.verifyStoragePermissions(EventsDashboard.this))
//                            helper.ImportData(EventsDashboard.this);
//                        else
//                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.permission_required),Toast.LENGTH_SHORT).
//                                    show();
//                        break;
//                    case R.id.help:
//                        break;
//
//                }
//                return true;
//            }
//
//        });


        openDialog=(CardView)findViewById(R.id.openDialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        registerEvent=(CardView) dialog.findViewById(R.id.eventRegisterButton);

        registerEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEventRegisteration();
            }
        });
    }

    private void RateMyApp() {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }

    }



    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayEvents=(RecyclerView)findViewById(R.id.displayEvents);

        try{
            displayEvents.removeAllViews();
        }
        catch (Exception e){

        }

        eventsAdapter= new EventsAdapter(com.omsoftonics.debtcred.EventsDashboard.this);

        try {
            displayEvents.setAdapter(eventsAdapter);
        }
        catch (Exception e){

        }
    }

    private void startEventRegisteration() {
        eventName=(EditText) dialog.findViewById(R.id.eventName);

        if(eventName.getText().length()==0){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.greator_than_zero), Toast.LENGTH_SHORT).show();
            eventName.setError(getString(R.string.name_greator_than_zero));
            return;
        }

        currentEventInformation=new EventInformation(eventName.getText().toString());
        if(currentEventInformation.SaveRecord(this)){
            dialog.cancel();
            currentEvents.add(0,currentEventInformation);
            eventsAdapter.notifyDataSetChanged();
            eventName.getText().clear();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE_PERMISSIONS == requestCode){

        }
    }

    private void StartBackUp() {
    }


}