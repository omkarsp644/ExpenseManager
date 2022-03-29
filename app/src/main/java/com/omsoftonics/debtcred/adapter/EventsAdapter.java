package com.omsoftonics.debtcred.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omsoftonics.debtcred.R;
import com.omsoftonics.debtcred.activity.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static com.omsoftonics.debtcred.MainActivity.currentEventInformation;
import static com.omsoftonics.debtcred.MainActivity.currentEvents;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private AppCompatActivity activity;


    public EventsAdapter(AppCompatActivity context) {
        this.activity = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.display_events, parent, false);


        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.eventName.setText(currentEvents.get(position).geteventName());
        viewHolder.eventdate.setText(currentEvents.get(position).getDateCreated());
        viewHolder.income.setText(Integer.toString(currentEvents.get(position).getEventIncome()));
        viewHolder.expanditure.setText(Integer.toString(currentEvents.get(position).getEventExp()));
        viewHolder.pending.setText(Integer.toString(currentEvents.get(position).getEventPending()));


        viewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentEventInformation=currentEvents.get(position);
                activity.startActivity(new Intent(activity, Dashboard.class));
            }
        });



        viewHolder.click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder alert=new AlertDialog.Builder(activity);
                alert.setTitle(R.string.want_to_delete);
                alert.setMessage(R.string.once_deleted);
                alert.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentEventInformation=currentEvents.get(position);
                        currentEventInformation.RemoveRecord(activity);
                        currentEvents.remove(position);
                        notifyDataSetChanged();
                        currentEventInformation.DeleteRecord(activity);
                        currentEventInformation=null;
                        Toast.makeText(activity,activity.getResources().getString(R.string.removed_successfully), Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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

    @Override
    public int getItemCount() {
        if(currentEvents==null){
            return 0;
        }
        return currentEvents.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventName;
        private TextView eventdate;
        private TextView income;
        private TextView expanditure;
        private TextView pending;

        private LinearLayout click;

//        private TextView printData;



        public ViewHolder(View v) {
            super(v);
            eventName = (TextView) v.findViewById(R.id.displayEventName);
            eventdate = (TextView) v.findViewById(R.id.displayEventDate);
            income = (TextView) v.findViewById(R.id.displayEventIncome);
            expanditure = (TextView) v.findViewById(R.id.displayEventExpanditure);
            click=(LinearLayout)v.findViewById(R.id.click);
            pending=(TextView)v.findViewById(R.id.displayEventPending);


        }
    }
}
