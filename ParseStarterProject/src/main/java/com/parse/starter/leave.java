package com.parse.starter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class leave extends AppCompatActivity {
    Calendar clndr;
    EditText dol,doa;
    EditText roll,name,phn,rsn,dest;
    int d,m,y;
    public void cancel(View view){
        Intent i=new Intent(getApplicationContext(),student.class);
        startActivity(i);
    }
    public void submit(View view) throws ParseException {
            if (roll.getText().toString().matches("") || name.getText().toString().matches("") || phn.getText().toString().matches("") || dol.getText().toString().matches("") || doa.getText().toString().matches("") || dest.getText().toString().matches("") || rsn.getText().toString().matches("")) {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            }
            else{
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

                Date date1 = format.parse(dol.getText().toString());
                Date date2 = format.parse(doa.getText().toString());
                Date cdate = format.parse(format.format(new Date() ));

                if(!(roll.getText().toString().matches(ParseUser.getCurrentUser().getUsername()))){
                    Toast.makeText(this, "Roll number doesn't belong to you", Toast.LENGTH_SHORT).show();
                }
                else if (date2.compareTo(date1) <= 0 || date1.compareTo(cdate) < 0) {
                    Toast.makeText(this, "Dates are not correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    ParseObject leave = new ParseObject("leave");
                    leave.put("roll", ParseUser.getCurrentUser().getUsername());
                    leave.put("mentor", ParseUser.getCurrentUser().get("mentor"));
                    leave.put("name",name.getText().toString());
                    leave.put("phone",phn.getText().toString());
                    leave.put("DOL",dol.getText().toString() );
                    leave.put("DOA",doa.getText().toString() );
                    leave.put("destination", dest.getText().toString());
                    leave.put("reason", rsn.getText().toString());
                    leave.put("status","sent");
                    leave.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                Toast.makeText(leave.this, "Your leave request has been sent to your mentor successfully", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),student.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(leave.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),student.class);
                                startActivity(i);
                            }
                        }
                    });
                }
            }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        setTitle("Leave Application Form");
        dol=findViewById(R.id.editText3);
        doa=findViewById(R.id.editText4);
        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        phn = findViewById(R.id.phn);
        rsn = findViewById(R.id.editText5);
        dest= findViewById(R.id.editText6);
        clndr=Calendar.getInstance();
        d=clndr.get(Calendar.DAY_OF_MONTH);
        m=clndr.get(Calendar.MONTH);
        y=clndr.get(Calendar.YEAR);
        dol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpg=new DatePickerDialog(leave.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        dol.setText(i+"/"+i1+"/"+i2);
                    }
                },y,m,d);
                dpg.show();
            }
        });
        doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpg=new DatePickerDialog(leave.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        doa.setText(i+"/"+i1+"/"+i2);
                    }
                },y,m,d);
                dpg.show();
            }
        });
        }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),student.class);
        startActivity(i);
        super.onBackPressed();
    }
    }
