package com.example.user.tracker.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.tracker.POJO.ShopDetails;
import com.example.user.tracker.R;

import java.util.ArrayList;


public class VisitedAdapter extends RecyclerView.Adapter<VisitedAdapter.MyViewHolder> {

    ArrayList<ShopDetails> shopDet=new ArrayList<>();


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return shopDet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shopname,inTime,inLocation,outTime,outLocation;


        public MyViewHolder(View itemView) {
            super(itemView);

            shopname=(TextView)itemView.findViewById(R.id.tv_shopname);
            inTime=(TextView)itemView.findViewById(R.id.tv_inTime);
            inLocation=(TextView)itemView.findViewById(R.id.tv_InLocation);
            outTime=(TextView)itemView.findViewById(R.id.tv_out_Time);
            outLocation=(TextView)itemView.findViewById(R.id.tv_outLocation);

        }
    }
}
