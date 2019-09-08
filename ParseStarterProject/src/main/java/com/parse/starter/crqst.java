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

public class crqst extends AppCompatActivity {
    TextView rollno,name,sec,sub,des,d;
    Button accept,reject;
    boolean mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crqst);
        rollno=findViewById(R.id.roll);
        name=findViewById(R.id.name);
        sec=findViewById(R.id.textView7);
        sub=findViewById(R.id.textView8);
        des=findViewById(R.id.textView4);
        accept=findViewById(R.id.button11);
        reject=findViewById(R.id.button12);
        d=findViewById(R.id.textView10);
        if(ParseUser.getCurrentUser().getString("studentorfaculty").matches("faculty")){
            mentor=true;
        }
        else{
            mentor=false;
        }

        Intent i = getIntent();
        String uni = i.getStringExtra("uni");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("complains");
        query.whereEqualTo("objectId", uni);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        rollno.setText(objects.get(0).getString("roll"));
                        name.setText(objects.get(0).getString("name"));
                        sec.setText("Section - " + objects.get(0).getString("section"));
                        sub.setText("Subject - " + objects.get(0).getString("subject"));
                        des.setText(objects.get(0).getString("description"));
                        String s = objects.get(0).get("status").toString();
                        if (!(s.matches("sent"))) {
                            accept.setVisibility(View.INVISIBLE);
                            reject.setVisibility(View.INVISIBLE);
                            d.setText(s);
                            d.setVisibility(View.VISIBLE);
                        } else {
                            if (mentor) {
                                accept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        accept.setVisibility(View.INVISIBLE);
                                        reject.setVisibility(View.INVISIBLE);
                                        d.setText("Accepted");
                                        d.setVisibility(View.VISIBLE);
                                        objects.get(0).put("status", "Accepted");
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
                                        objects.get(0).put("status", "Rejected");
                                        objects.get(0).saveInBackground();
                                    }
                                });
                            } else {
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
                    Toast.makeText(crqst.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i;
                    if(mentor) {
                        i = new Intent(getApplicationContext(), complains.class);
                    }else{
                        i = new Intent(getApplicationContext(), cliist.class);
                    }
                    startActivity(i);
                }
            }

        });
        setTitle("Complaint Request Form");
    }
    @Override
    public void onBackPressed() {
        Intent i;
        if(mentor) {
            i = new Intent(getApplicationContext(), complains.class);
        }else{
            i = new Intent(getApplicationContext(), cliist.class);
        }
        startActivity(i);
        super.onBackPressed();
    }
}
