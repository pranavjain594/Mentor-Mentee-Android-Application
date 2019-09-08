package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class lrqst extends AppCompatActivity {
    TextView rollno,name,phn,dol,doa,prps,dest,d;
    Button accept,reject;
    boolean mentor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrqst);
        rollno=findViewById(R.id.roll);
        name=findViewById(R.id.name);
        phn=findViewById(R.id.textView7);
        dol=findViewById(R.id.textView8);
        doa=findViewById(R.id.textView4);
        prps=findViewById(R.id.textView5);
        dest=findViewById(R.id.textView6);
        d=findViewById(R.id.textView9);
        accept=findViewById(R.id.button11);
        reject=findViewById(R.id.button12);
        if(ParseUser.getCurrentUser().getString("studentorfaculty").matches("faculty")){
        mentor=true;
        }
        else{
            mentor=false;
        }

        Intent i = getIntent();
        String uni = i.getStringExtra("uni");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("leave");
        query.whereEqualTo("objectId", uni);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        rollno.setText(objects.get(0).getString("roll"));
                        name.setText(objects.get(0).getString("name"));
                        phn.setText("Ph.No. while Leaving- " + objects.get(0).getString("phone"));
                        dol.setText("Date of Leaving- " + objects.get(0).getString("DOL"));
                        doa.setText("Date of Arrival- " + objects.get(0).getString("DOA"));
                        prps.setText("Reason- " + objects.get(0).getString("reason"));
                        dest.setText("Destination- " + objects.get(0).getString("destination"));
                        String s = objects.get(0).get("status").toString();
                        if (!(s.matches("sent"))) {
                            accept.setVisibility(View.INVISIBLE);
                            reject.setVisibility(View.INVISIBLE);
                            if (mentor) {
                                d.setText(s.substring(0, 9));
                            } else {
                                d.setText(s);
                            }
                            d.setVisibility(View.VISIBLE);
                        } else {
                            if(mentor){
                            accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    accept.setVisibility(View.INVISIBLE);
                                    reject.setVisibility(View.INVISIBLE);
                                    d.setText("Accepted");
                                    d.setVisibility(View.VISIBLE);
                                    objects.get(0).put("status", "Accepted by Mentor");
                                    objects.get(0).put("dean", "acceptedbymentor");
                                    objects.get(0).saveInBackground();
                                }
                            });
                            reject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    accept.setVisibility(View.INVISIBLE);
                                    reject.setVisibility(View.INVISIBLE);
                                    d.setText("Rejected");
                                    d.setVisibility(View.VISIBLE);
                                    objects.get(0).put("status", "Rejected by Mentor");
                                    objects.get(0).put("dean", "rejectedbymentor");
                                    objects.get(0).saveInBackground();
                                }
                            });
                        }else{
                                accept.setVisibility(View.INVISIBLE);
                                reject.setVisibility(View.INVISIBLE);
                                d.setText("Pending...");
                                d.setVisibility(View.VISIBLE);
                                objects.get(0).saveInBackground();
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(lrqst.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i;
                    if(mentor) {
                        i = new Intent(getApplicationContext(), leaves.class);
                    }else{
                        String s=ParseUser.getCurrentUser().getString("studentorfaculty");
                        if(s.matches("student")) {
                            i = new Intent(getApplicationContext(), lliist.class);
                        }else{
                            i = new Intent(getApplicationContext(), hostel.class);
                        }
                    }
                    startActivity(i);
                }
            }

        });
    setTitle("Leave request form");
    }
    @Override
    public void onBackPressed() {
        Intent i;
        if(mentor) {
            i = new Intent(getApplicationContext(), leaves.class);
        }else{
            String s=ParseUser.getCurrentUser().getString("studentorfaculty");
            if(s.matches("student")) {
                i = new Intent(getApplicationContext(), lliist.class);
            }else{
                i = new Intent(getApplicationContext(), hostel.class);
            }
        }
        startActivity(i);
        super.onBackPressed();
    }
}
