package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;

public class student extends AppCompatActivity {
    public void leave(View view){
        Intent i=new Intent(getApplicationContext(),leave.class);
        startActivity(i);
    }
    public void complain(View view){
        Intent i=new Intent(getApplicationContext(),Complain.class);
        startActivity(i);
    }
    public void status(View view){
        Intent i=new Intent(getApplicationContext(),ststus.class);
        startActivity(i);
    }
    public void edit(View view){
        Intent i=new Intent(getApplicationContext(),editprofile.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.logout, menu);
        MenuItem item = menu.findItem(R.id.editprofile);
        item.setVisible(false);

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
        else if(item.getItemId() == R.id.mentorprofile){
            Intent k=new Intent(getApplicationContext(),mentorprofile.class);
            startActivity(k);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setTitle("Hey "+ ParseUser.getCurrentUser().get("name")+"!");
    }
    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
    }
}
