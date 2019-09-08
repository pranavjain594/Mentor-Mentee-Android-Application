package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> m;
        String mntr;
        public void signup(View view){
            final EditText roll = findViewById(R.id.roll);
            final EditText name = findViewById(R.id.name);
            final EditText phone = findViewById(R.id.phn);
            final EditText email = findViewById(R.id.email);
            final EditText pswrd = findViewById(R.id.editText8);
            final EditText cpswrd = findViewById(R.id.editText11);
            ParseUser.logOut();
            if (roll.getText().toString().matches("") || name.getText().toString().matches("") || phone.getText().toString().matches("") || email.getText().toString().matches("") || pswrd.getText().toString().matches("") || cpswrd.getText().toString().matches("") || mntr.matches("Select Mentor")) {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            } else {
                    ParseUser user = new ParseUser();
                    if(pswrd.getText().toString().matches(cpswrd.getText().toString())) {
                        user.setUsername(roll.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setPassword(pswrd.getText().toString());
                    }
                    else{
                        Toast.makeText(signup.this, "Confirm Password is wrong", Toast.LENGTH_SHORT).show();
                    }
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                ParseUser.getCurrentUser().put("name",name.getText().toString());
                                ParseUser.getCurrentUser().put("phone",phone.getText().toString());
                                ParseUser.getCurrentUser().put("studentorfaculty","student");
                                ParseUser.getCurrentUser().put("currentpassword",pswrd.getText().toString());
                                ParseUser.getCurrentUser().put("mentor",mntr);
                                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Toast.makeText(signup.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(i);
                                    }
                                });
                            } else {
                                Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("SignUp");
        final Spinner s = (Spinner) findViewById(R.id.spinner);
        m=new ArrayList();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(signup.this,
                android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereEqualTo("studentorfaculty","faculty");
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        m.clear();
                        m.add("Select Mentor");
                        for(ParseUser user:objects){
                            m.add(user.getString("name"));
                        }
                        adapter.notifyDataSetChanged();
                        s.setSelection(-1);
                    }
                }
                else{
                    Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             mntr = m.get(i).toString();
        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(signup.this,"Select Your Mentor", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
