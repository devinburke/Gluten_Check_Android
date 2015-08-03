package com.stocktwt.glutenchecker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;


    List<information> data = Collections.emptyList();



    public VivzAdapter (Context context, List<information> data){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    public void delete(int position){
       data.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.customer_row, parent, false);
        Log.d("Vivz", "oncreatcalled");
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {

        information current = data.get(position);
        Log.d("Vivz", "onbindbiewholdercalled" + position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.icondID);





    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder (View itemView){
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);
            //icon.setOnClickListener(this);



        }

    }

}
