package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Complain extends AppCompatActivity {
    EditText roll,name,sec,sub,des;
    public void cancel(View view){
        Intent i=new Intent(getApplicationContext(),student.class);
        startActivity(i);
    }
    public void submit(View view){
        if (roll.getText().toString().matches("") || name.getText().toString().matches("") || sec.getText().toString().matches("") || sub.getText().toString().matches("") || des.getText().toString().matches("") ) {
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
        }
        else{
            if(!(roll.getText().toString().matches(ParseUser.getCurrentUser().getUsername()))){
                Toast.makeText(this, "Roll number doesn't belong to you", Toast.LENGTH_SHORT).show();
            }
            else {
                ParseObject complains = new ParseObject("complains");
                complains.put("roll", ParseUser.getCurrentUser().getUsername());
                complains.put("mentor", ParseUser.getCurrentUser().get("mentor"));
                complains.put("name",name.getText().toString());
                complains.put("section",sec.getText().toString());
                complains.put("subject",sub.getText().toString() );
                complains.put("description",des.getText().toString() );
                complains.put("status","sent");
                complains.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            Toast.makeText(Complain.this, "Your Complaint has been sent to your mentor successfully", Toast.LENGTH_LONG).show();
                            Intent i=new Intent(getApplicationContext(),student.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(Complain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_complain);
        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        sec = findViewById(R.id.editText5);
        sub = findViewById(R.id.editText7);
        des= findViewById(R.id.editText9);
        setTitle("Complaint Form");
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),student.class);
        startActivity(i);
        super.onBackPressed();
    }
}
