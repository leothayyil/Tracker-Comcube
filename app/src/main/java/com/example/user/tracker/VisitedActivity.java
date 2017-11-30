package com.example.user.tracker;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.tracker.Adapter.VisitedAdapter;
import com.example.user.tracker.POJO.ShopDetails;

public class VisitedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper myDb;
    StringBuffer id,shopName,inTime,inLocation,outTime,outLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visited);

     recyclerView=(RecyclerView)findViewById(R.id.recyclerId);

     recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        VisitedAdapter adapter=new VisitedAdapter();
        recyclerView.setAdapter(adapter);



        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            Toast.makeText(VisitedActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {


          id=  buffer.append("ID :" + res.getString(0) + "\n");
           shopName= buffer.append("SHOP :" + res.getString(1) + "\n");
          inTime=  buffer.append("IN_TIME :" + res.getString(2) + "\n");
          inLocation=  buffer.append("IN_LOCATION :" + res.getString(3) + "\n");
           outTime= buffer.append("OUT_TIME :" + res.getString(4) + "\n");
           outLocation= buffer.append("OUT_LOCATION :" + res.getString(5) + "\n\n");

           ShopDetails shopDetails=new ShopDetails(id,shopName,inTime,inLocation,outTime,outLocation);


        }
    }
}
