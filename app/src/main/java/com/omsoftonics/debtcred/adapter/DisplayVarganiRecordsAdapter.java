package com.omsoftonics.debtcred.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omsoftonics.debtcred.R;
import com.omsoftonics.debtcred.model.VarganiReciepts;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static com.omsoftonics.debtcred.MainActivity.currentEventInformation;
import static com.omsoftonics.debtcred.activity.Registervargani.indexValue;
import static com.omsoftonics.debtcred.activity.Registervargani.isFromRecord;
import static com.omsoftonics.debtcred.model.EventInformation.GetCurrentdate;

public class DisplayVarganiRecordsAdapter extends RecyclerView.Adapter<DisplayVarganiRecordsAdapter.ViewHolder> {

    private AppCompatActivity activity;
    private ArrayList<VarganiReciepts> varganireciepts;


    public DisplayVarganiRecordsAdapter(AppCompatActivity context, ArrayList<VarganiReciepts> varga) {
        this.activity = context;
        this.varganireciepts=varga;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.display_vargani_items, parent, false);


        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {




        position=varganireciepts.size()-1-position;

        varganireciepts.get(position).setIndex(position);


        viewHolder.recieptID.setText(Integer.toString(varganireciepts.get(position).getRecieptNumber()));
        viewHolder.name.setText(varganireciepts.get(position).getName());
        viewHolder.amount.setText(Integer.toString(varganireciepts.get(position).getAmount()));

        viewHolder.date.setText(varganireciepts.get(position).getDateCreated());
        String s=varganireciepts.get(position).getDatePaid()==null?"NOT PAID":varganireciepts.get(position).getDatePaid();
        viewHolder.datepaid.setText(s);



        if(varganireciepts.get(position).getDatePaid()==null){
            viewHolder.click.setBackgroundColor(activity.getResources().getColor(R.color.redDisplay));
        }
        else{
            viewHolder.click.setBackgroundColor(activity.getResources().getColor(R.color.greenDisplay));
        }


        int finalPosition = position;
        viewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(activity,"ID :"+varganireciepts.get(finalPosition).getIndex()+"   "+varganireciepts.get(finalPosition), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert=new AlertDialog.Builder(activity);
                alert.setTitle("Want to Edit Data...?");
                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditValues(finalPosition);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.create().show();
            }
        });


        int finalPosition1 = position;
        viewHolder.click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(activity);
                alert.setTitle(R.string.want_to_delete);
                alert.setMessage(R.string.once_deleted);
                alert.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        varganireciepts.get(finalPosition1).RemoveRecord(activity);
                        currentEventInformation.DeleteVarganiRecord(varganireciepts.get(finalPosition1));
                        currentEventInformation.UpdateRecord(activity);
                        notifyDataSetChanged();

                    }
                });

                alert.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alert.setCancelable(false);
                alert.create().show();


                return true;
            }
        });


    }

    private void EditValues(int finalPosition) {
        EditText id=(EditText) activity.findViewById(R.id.recieptID);
        id.setText(Integer.toString(varganireciepts.get(finalPosition).getRecieptNumber()));
        TextView currentTime=(TextView) activity.findViewById(R.id.currentDate);
        currentTime.setText(varganireciepts.get(finalPosition).getDateCreated());
        TextView datePaid=(TextView) activity.findViewById(R.id.datePaid);
        CheckBox chk=(CheckBox)activity.findViewById(R.id.paidCheck);
        if(varganireciepts.get(finalPosition).getDatePaid()==null){
            chk.setChecked(false);
            currentTime.setText(varganireciepts.get(finalPosition).getDateCreated());
            datePaid.setText(GetCurrentdate());
        }
        else{
            chk.setChecked(true);
            currentTime.setText(varganireciepts.get(finalPosition).getDateCreated());
            datePaid.setText(varganireciepts.get(finalPosition).getDatePaid());
        }

        EditText name=(EditText)activity.findViewById(R.id.name);
        name.setText(varganireciepts.get(finalPosition).getName());

//        TextView dateCreated=(TextView)activity.findViewById(R.id.currentDate);


        EditText amount=(EditText)activity.findViewById(R.id.amountPaid);
        amount.setText(Integer.toString(varganireciepts.get(finalPosition).getAmount()));

        isFromRecord=true;

        indexValue=varganireciepts.get(finalPosition).getIndex();
    }

    @Override
    public int getItemCount() {
        if(varganireciepts==null){
            return 0;
        }
        else {
            return varganireciepts.size();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView recieptID;
        private TextView name;
        private TextView amount;
        private TextView date;
        private TextView datepaid;


        private LinearLayout click;



        public ViewHolder(View v) {
            super(v);
            recieptID = (TextView) v.findViewById(R.id.reciept_ID);
            name = (TextView) v.findViewById(R.id.reciept_Name);
            amount = (TextView) v.findViewById(R.id.reciept_Amount);
            click=(LinearLayout)v.findViewById(R.id.clickItem);

            date=(TextView)v.findViewById(R.id.reciept_date);
            datepaid=(TextView)v.findViewById(R.id.reciept_datePaid);

        }
    }
}
