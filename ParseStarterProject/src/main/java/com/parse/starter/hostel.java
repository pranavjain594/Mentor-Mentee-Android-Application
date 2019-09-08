package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class hostel extends AppCompatActivity {
    ListView mlv;
    ArrayList<String> a,uni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);
        setTitle("Leave Requests");
        mlv=findViewById(R.id.mlv);
        a=new ArrayList<>();
        uni=new ArrayList<>();
        final ArrayAdapter<String> ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
        mlv.setAdapter(ad);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("leave");
        query.orderByDescending("createdAt");
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
                            String r = request.getString("roll")+"\n"+df.format(date);
                            String u = request.getObjectId();
                            a.add(r);
                            uni.add(u);
                        }
                        ad.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(hostel.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.logout, menu);
        MenuItem item = menu.findItem(R.id.mentorprofile);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.editprofile);
        item2.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Are You Sure?");
            builder.setPositiveButton("logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ParseUser.logOut();
                    Intent k=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(k);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
    }
}
