package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class leaves extends AppCompatActivity {
    ListView mlv;
    ArrayList<String> a,uni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);
        mlv=findViewById(R.id.mlv);
        a=new ArrayList<>();
        uni=new ArrayList<>();
        final ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        mlv.setAdapter(ad);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("leave");
        query.orderByDescending("createdAt");
        query.whereEqualTo("mentor", ParseUser.getCurrentUser().get("name"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        a.clear();
                        uni.clear();
                        for (ParseObject request : objects) {
                            Date date = request.getCreatedAt();
                            DateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy  HH:mm");
                            String r = request.getString("roll")+"\n"+df.format(date);;
                            String u = request.getObjectId();
                            a.add(r);
                            uni.add(u);
                        }
                        ad.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(leaves.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),lrqst.class);
                intent.putExtra("uni", uni.get(i));
                startActivity(intent);
            }
        });
        setTitle("Leave Requests");
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),mentor.class);
        startActivity(i);
        super.onBackPressed();
    }
}
