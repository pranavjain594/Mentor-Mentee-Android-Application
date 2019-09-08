/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt;
    String userType;
    EditText username,password;

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.textView3){
            Intent i=new Intent(getApplicationContext(),signup.class);
            startActivity(i);
        }
    }

    public void login(View view) {
            if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            }
            else {
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            String s=user.getString("studentorfaculty");
                               if (s.matches("student")){
                                   Intent j=new Intent(getApplicationContext(),student.class);
                                   startActivity(j);
                               }else if(s.matches("faculty")){
                                   Intent k=new Intent(getApplicationContext(),mentor.class);
                                   startActivity(k);
                               }else if (s.matches("dean")){
                                   Intent l=new Intent(getApplicationContext(),dean.class);
                                   startActivity(l);
                               }else{
                                   Intent l=new Intent(getApplicationContext(),hostel.class);
                                   startActivity(l);
                               }
                               Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                           }
                        else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
      username=findViewById(R.id.editText);
      password=findViewById(R.id.editText2);
      txt=(TextView)findViewById(R.id.textView3);


      txt.setOnClickListener(this);
      if(ParseUser.getCurrentUser()!=null)
      {   String s=ParseUser.getCurrentUser().getString("studentorfaculty");
          if(s.matches("student")){
              Intent i=new Intent(getApplicationContext(),student.class);
              startActivity(i);
          }else if(s.matches("faculty")){
              Intent i=new Intent(getApplicationContext(),mentor.class);
              startActivity(i);
          }else if(s.matches("dean")){
              Intent i=new Intent(getApplicationContext(),dean.class);
              startActivity(i);
          }else{
          Intent i=new Intent(getApplicationContext(),hostel.class);
          startActivity(i);
      }
      }
      setTitle("Login");

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

  }

}