package com.omsoftonics.debtcred.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omsoftonics.debtcred.R;
import com.omsoftonics.debtcred.adapter.DisplayVarganiRecordsAdapter;
import com.omsoftonics.debtcred.model.VarganiReciepts;
import com.omsoftonics.debtcred.sqlitehelper.SqliteDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.omsoftonics.debtcred.MainActivity.currentEventInformation;
import static com.omsoftonics.debtcred.model.EventInformation.GetCurrentdate;

public class Registervargani extends AppCompatActivity {

    EditText recieptID,payersName,payingAmount;
    CheckBox checkVarganiPaid;
    LinearLayout paid;
    TextView todaysDate,registerVargani,datePaid;

    DisplayVarganiRecordsAdapter adapter;
    RecyclerView displayVarganiRecords;
    int lastCount=0;
    public static boolean isFromRecord=false;
    public static int indexValue=0;
    SqliteDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vargani);

        checkVarganiPaid=(CheckBox)findViewById(R.id.paidCheck);
        paid=(LinearLayout)findViewById(R.id.varganiPaidorNot);
        helper=new SqliteDatabaseHelper(this);
        adapter=new DisplayVarganiRecordsAdapter(Registervargani.this,currentEventInformation.getVarganiList());
        displayVarganiRecords=(RecyclerView)findViewById(R.id.displayVarganiRecords);

        try {
            displayVarganiRecords.setAdapter(adapter);
        }
        catch (Exception e){

        }


        SearchView searchView=(SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                displayVarganiRecords.removeAllViews();

                adapter=new DisplayVarganiRecordsAdapter(Registervargani.this, helper.GetSpecificVarganis(query));
                displayVarganiRecords.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                displayVarganiRecords.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displayVarganiRecords.removeAllViews();

                adapter=new DisplayVarganiRecordsAdapter(Registervargani.this, helper.GetSpecificVarganis(newText));
                displayVarganiRecords.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                displayVarganiRecords.setAdapter(adapter);

                return true;
            }
        });

        checkVarganiPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    paid.setVisibility(View.VISIBLE);
                }
                else{
                    paid.setVisibility(View.GONE);
                }
            }
        });
        datePaid=(TextView) findViewById(R.id.datePaid);
        recieptID=(EditText) findViewById(R.id.recieptID);
        todaysDate=(TextView)findViewById(R.id.currentDate);
        payersName=(EditText)findViewById(R.id.name);
        payingAmount=(EditText)findViewById(R.id.amountPaid);
        registerVargani=(TextView)findViewById(R.id.registerVargani);

        recieptID.setText(Integer.toString(currentEventInformation.getVarganiList().size()+1));


        datePaid.setText(GetCurrentdate());
        todaysDate.setText(GetCurrentdate());


        datePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                setDateTimeField(c,datePaid) ;
            }
        });

//        todaysDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c=Calendar.getInstance();
//                setDateTimeField(c,todaysDate) ;
//            }
//        });

        registerVargani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerVarganiM();
            }
        });
    }

    private void registerVarganiM() {


        if(!isFromRecord && recieptID.getText().length()==0){
            recieptID.setError(getResources().getString(R.string.reciept_id_required));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.reciept_id_required), Toast.LENGTH_SHORT).show();
            return ;
        }

        if(!isFromRecord &&currentEventInformation.getVarganiList()!=null && recieptID.getText().length()!=0  && currentEventInformation.getVarganiList().stream().filter(p->(p.getRecieptNumber()== Integer.parseInt(recieptID.getText().toString()))).count()!=0){
            recieptID.setError(getResources().getString(R.string.reciept_id_taken_by_another));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.reciept_id_taken_by_another), Toast.LENGTH_SHORT).show();
            return;
        }

        if(payersName.getText().length()==0){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.payer_name_required), Toast.LENGTH_SHORT).show();
            payersName.setError(getResources().getString(R.string.payer_name_required));
            return;
        }

        if(payingAmount.getText().toString().equals("") || payingAmount.getText().toString()==null){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.amount_must_greator_than_zero), Toast.LENGTH_SHORT).show();
            payingAmount.setError(getResources().getString(R.string.amount_must_greator_than_zero));
            return;
        }
        if(!payingAmount.getText().toString().equals("") && Integer.parseInt(payingAmount.getText().toString())<=0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.amount_must_greator_than_zero), Toast.LENGTH_SHORT).show();
            payingAmount.setError(getResources().getString(R.string.amount_must_greator_than_zero));
            return;
        }


         VarganiReciepts reciepts=new VarganiReciepts();

        reciepts.setAmount(Integer.parseInt(payingAmount.getText().toString()));
        reciepts.setDateCreated(todaysDate.getText().toString());


        reciepts.setName(payersName.getText().toString());
        reciepts.setRecieptNumber(Integer.parseInt(recieptID.getText().toString()));
        if (checkVarganiPaid.isChecked()) {
            reciepts.setDatePaid(datePaid.getText().toString());
        } else {
            reciepts.setDatePaid(null);
        }



        reciepts.setEventID(currentEventInformation.getEventID());


            if (!isFromRecord) {
                lastCount = Integer.parseInt(recieptID.getText().toString());
                reciepts.SaveRecord(this);
                currentEventInformation.AddVargani(reciepts);

            } else {
                reciepts.setIndex(indexValue);
                isFromRecord = false;
                if(!(currentEventInformation.varganiList.get(reciepts.getIndex()).getDatePaid() == null && !checkVarganiPaid.isChecked()) || (currentEventInformation.varganiList.get(reciepts.getIndex()).getDatePaid() != null && checkVarganiPaid.isChecked())) {
                    currentEventInformation.UpdateCashInfo(reciepts);
                }


                currentEventInformation.checkChangeInCash(reciepts);
                currentEventInformation.UpdateVargani(reciepts);
                reciepts.UpdateRecord(this);
            }

            currentEventInformation.UpdateRecord(this);




        recieptID.setText(Integer.toString(lastCount+1));
        payersName.getText().clear();
        payingAmount.getText().clear();

        adapter.notifyDataSetChanged();

        adapter=new DisplayVarganiRecordsAdapter(Registervargani.this,currentEventInformation.getVarganiList());
        displayVarganiRecords=(RecyclerView)findViewById(R.id.displayVarganiRecords);

        try {
            displayVarganiRecords.setAdapter(adapter);
        }
        catch (Exception e){

        }
    }



    private void setDateTimeField(Calendar c, TextView v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth, 0, 0);
                v.setText(dateFormatter.format(c.getTime()));
            }

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}