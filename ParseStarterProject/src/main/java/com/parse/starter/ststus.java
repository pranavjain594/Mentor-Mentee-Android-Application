package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ststus extends AppCompatActivity {
    public void leave(View view){
        Intent i=new Intent(getApplicationContext(),lliist.class);
        startActivity(i);
    }
    public void complaint(View view){
        Intent i=new Intent(getApplicationContext(),cliist.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ststus);
        setTitle("Requests");
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),student.class);
        startActivity(i);
        super.onBackPressed();
    }
}
