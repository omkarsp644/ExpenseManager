package com.omsoftonics.debtcred.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.omsoftonics.debtcred.R;
import com.omsoftonics.debtcred.adapter.DisplayExpenseRecordsAdapter;
import com.omsoftonics.debtcred.model.Expanditures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static com.omsoftonics.debtcred.MainActivity.currentEventInformation;
import static com.omsoftonics.debtcred.model.EventInformation.GetCurrentdate;

public class RegisterExpense extends AppCompatActivity {

    TextView currentDate;
    EditText name,expenseForWhat,amount;
    TextView addExpense;
    RecyclerView displayExpense;
    DisplayExpenseRecordsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_expense);



        currentDate=(TextView)findViewById(R.id.expense_currentDate);
        currentDate.setText(GetCurrentdate());

        name=(EditText)findViewById(R.id.expense_personname);
        expenseForWhat=(EditText)findViewById(R.id.expense_forwhat);
        amount=(EditText)findViewById(R.id.expense_amountPaid);

        addExpense=(TextView)findViewById(R.id.registerExpense);


        displayExpense=(RecyclerView)findViewById(R.id.displayExpenseRecords);
        adapter=new DisplayExpenseRecordsAdapter(this);
        try {
            displayExpense.setAdapter(adapter);
        }
        catch (Exception e){

        }
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerExpense();
            }
        });
    }

    private void registerExpense() {
        if(amount.getText().toString()==null || amount.getText().toString().equals("") || Integer.parseInt(amount.getText().toString())==0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.amount_greator_than_zero), Toast.LENGTH_SHORT).show();
            amount.setError(getResources().getString(R.string.amount_greator_than_zero));
            return;
        }

        if(expenseForWhat.getText().length()==0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.expense_for_what_req), Toast.LENGTH_SHORT).show();
            expenseForWhat.setError(getString(R.string.expense_for_w));
            return;
        }

        Expanditures e=new Expanditures();
        e.setAmount(Integer.parseInt(amount.getText().toString()));
        e.setDate(currentDate.getText().toString());
        e.setGivenFor(expenseForWhat.getText().toString());
        e.setGivenTo(name.getText().toString());
        e.setEventID(currentEventInformation.getEventID());
       e.setIndex(currentEventInformation.getExpanditures().size());


        currentEventInformation.AddExpense(e);
        e.SaveRecord(this);
        currentEventInformation.UpdateRecord(this);
        adapter.notifyDataSetChanged();



        amount.getText().clear();
        currentDate.setText(GetCurrentdate());
        expenseForWhat.getText().clear();
        name.getText().clear();
        //id.setText(currentEventInformation.GetNextID());


    }


}